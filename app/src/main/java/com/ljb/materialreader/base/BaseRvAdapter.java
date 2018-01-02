package com.ljb.materialreader.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljb.materialreader.R;
import com.ljb.materialreader.ui.widget.LoadMoreView;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : RecyclerView的Adapter，也是Rv所有Adapter的基类
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mDatas;
    protected Context mContext;
    private int layoutId;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    public static final int TYPE_EMPTY = 10000000;
    public static final int TYPE_COMMON = 10000001;
    public static final int TYPE_LOAD = 10000002;
    private FloatingActionButton mFab;
    private onLoadMoreListener mOnLoadMoreListener;
    private LoadMoreView mLoadMoreView;

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        mLoadMoreView = loadMoreView;
    }

    public void setEnableEndLoadMore(boolean enableEndLoadMore) {
        mEnableEndLoadMore = enableEndLoadMore;
    }

    private boolean mEnableEndLoadMore;

    public void setOnLoadMoreListener(onLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRvAdapter(List<T> datas, int layoutId, Context context) {
        mContext = context;
        mDatas = datas;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        if (viewType == TYPE_COMMON) {
            View view = mInflater.inflate(layoutId, parent, false);
            holder = new BaseViewHolder(view);
        } else if (viewType == TYPE_EMPTY) {
            View view = mInflater.inflate(R.layout.item_empty, parent, false);
            holder = new BaseViewHolder(view);
        } else if (viewType == TYPE_LOAD) {
            holder = getLoadView(parent);
        }
        return holder;
    }

    public BaseViewHolder getLoadView(ViewGroup parent) {
        View view = mInflater.inflate(mLoadMoreView.getLoadView(), parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_FAIL) {
                    if (mOnLoadMoreListener != null) {
                        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
                        notifyItemChanged(getItemCount() - 1);
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
                if (mEnableEndLoadMore && mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_END) {
                    if (mOnLoadMoreListener != null) {
                        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
                        notifyItemChanged(getItemCount() - 1);
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_COMMON) {
            T t = mDatas.get(position);
            convert(holder, t);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, BaseRvAdapter.this);
                    }
                });

            }
        } else if (holder.getItemViewType() == TYPE_LOAD) {
            mLoadMoreView.convert(holder);
        }
    }


    protected abstract void convert(BaseViewHolder holder, T data);

    @Override
    public int getItemViewType(int position) {
        if (mDatas == null || mDatas.isEmpty()) {
            return TYPE_EMPTY;

        }
        if (isFooterView(position)) {
            return TYPE_LOAD;
        }
        return TYPE_COMMON;
    }

    /**
     * 判断是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isFooterView(int position) {
        return mLoadMoreView != null && position >= getItemCount() - 1 && getItemCount() > 1;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.isEmpty()) {
            return 1;

        }
        return mDatas.size() + getFooterViewCount();
    }

    private int getFooterViewCount() {
        return mLoadMoreView != null ? 1 : 0;
    }

    public void bindFab(FloatingActionButton fab) {
        if (fab != null) {
            mFab = fab;
        }

    }

    /**
     * 添加更多数据
     *
     * @param data
     */
    public void addMoreData(@NonNull List<T> data) {
        int size = mDatas.size();
        mDatas.addAll(data);
        notifyItemInserted(size);
    }

    /**
     * 在某个位置添加数据
     *
     * @param position
     * @param data
     */
    public void addDataInPosition(int position, @NonNull List<T> data) {
        mDatas.addAll(position, data);
        notifyDataSetChanged();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = findLastVisibleItemPosition(layoutManager);
                if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                        && layoutManager.getItemCount() > layoutManager.getChildCount()) {
                    if (mLoadMoreView != null && mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_DEFAULT &&
                            mOnLoadMoreListener != null) {//加载更多
                        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
                        notifyItemChanged(getItemCount() - 1);
                        mOnLoadMoreListener.onLoadMore();
                    }

                }
                if (mFab != null) {
                    if (dy < 0) {
                        mFab.show();
                    } else if(dy>0){
                        mFab.hide();
                    }

                }
            }


        });
    }

    public void setLoadEnd() {
        if (mLoadMoreView == null) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_END);
        notifyItemChanged(getItemCount() - 1);
    }

    public void setLoadComplete() {
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        notifyItemChanged(getItemCount() - 1);
    }

    public void setLoadFail() {
        if (mLoadMoreView == null) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_FAIL);
        notifyItemChanged(getItemCount() - 1);
    }

    /**
     * 返回最后一个可见Item的position
     *
     * @param layoutManager
     * @return
     */
    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = 0;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] info = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(info);
            lastVisibleItemPosition = findMax(info);
        } else {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    private int findMax(int[] info) {
        int max = info[0];
        for (int value : info) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, BaseRvAdapter adapter);
    }

    /**
     * 加载更多监听器
     */
    public interface onLoadMoreListener {
        void onLoadMore();
    }
}
