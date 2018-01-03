package com.ljb.materialreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;
import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseViewHolder;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description : 发现页面列表适配器
 */

public class DiscoverAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    //头部搜索类型
    public static final int TYPE_SEARCH_HEADER = 0;
    //flowTag布局类型
    public static final int TYPE_HOT_SEARCH = 1;
    private List<String> mTags;
    private Context mContext;
    private final LayoutInflater mInflater;
    private OnTagClickListener mOnTagClickListener;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public DiscoverAdapter(List<String> datas, Context context) {
        mContext = context;
        mTags = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_SEARCH_HEADER:
                view = mInflater.inflate(R.layout.item_discover_search, parent, false);
                holder = new BaseViewHolder(view);
                break;
            case TYPE_HOT_SEARCH:
                view = mInflater.inflate(R.layout.item_hot_search, parent, false);
                holder = new BaseViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HOT_SEARCH://热标签布局
                //实例化相关控件
                final TagFlowLayout tagFlowLayout = holder.getView(R.id.tag_flag_layout);
                //设置tag点击事件
                tagFlowLayout.setTagListener(new com.github.hymanme.tagflowlayout.OnTagClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (mOnTagClickListener != null) {
                            mOnTagClickListener.onTagClick(tagFlowLayout, view, position);
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                });
                TagAdapter adapter = new TagAdapter() {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        DefaultTagView textview = new ColorfulTagView(mContext);
                        textview.setText(mTags.get(position));
                        return textview;
                    }
                };
                //设置适配器
                tagFlowLayout.setTagAdapter(adapter);
                //绑定数据
                adapter.addAllTags(mTags);
                break;
            case TYPE_SEARCH_HEADER://搜索
                TextView tv = holder.getView(R.id.tv_search);
                ImageView iv_scan = holder.getView(R.id.iv_scan);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClick(v);
                        }
                    }
                });
                iv_scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onClick(v);
                        }
                    }
                });
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SEARCH_HEADER;
        } else {
            return TYPE_HOT_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public interface OnTagClickListener {
        void onTagClick(TagFlowLayout parent, View view, int position);
    }


    public interface OnItemClickListener {
        void onClick(View view);
    }
}
