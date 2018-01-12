package com.ljb.materialreader.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.BookListAdater;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.bean.response.douban.BookListResponse;
import com.ljb.materialreader.ui.activity.BookDetailActivity;
import com.ljb.materialreader.ui.presenter.BookListPresenter;
import com.ljb.materialreader.ui.view.BookListView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.ResourceUtils;
import com.ljb.materialreader.utils.SnUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/9
 * Description : 分类详情列表界面
 */

public class CategoryDetailFragment extends BaseFragment<BookListPresenter> implements BookListView {
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,"
            + "summary,images,pages,price,binding,isbn13";
    private static int count = 10;
    private static int page = 0;
    private String tag;
    private GridLayoutManager mLayoutManager;
    private List<BookInfoResponse> mBookInfoResponses;
    private BookListAdater mAdater;

    public static CategoryDetailFragment newInstance(String child) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("child", child);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_detail;
    }

    @Override
    protected BookListPresenter getPresenter() {
        return new BookListPresenter(this, this);
    }

    @Override
    protected void initView() {
        tag = getArguments().getString("child");

        //设置布局管理器
        int spanCount = ResourceUtils.getInteger(R.integer.home_span_count);
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRvContent.setLayoutManager(mLayoutManager);

        //设置适配器
        mBookInfoResponses = new ArrayList<>();
        mAdater = new BookListAdater(mBookInfoResponses, getActivity(), spanCount);
        mRvContent.setAdapter(mAdater);
        //添加动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        //设置加载布局
        mAdater.setLoadMoreView(new LoadMoreView() {
            @Override
            public int getLoadView() {
                return R.layout.layout_load_view;
            }

            @Override
            public int getLoadingViewId() {
                return R.id.loading_view;
            }

            @Override
            public int getLoadFailViewId() {
                return R.id.load_failure_view;
            }

            @Override
            public int getLoddEndViewId() {
                return R.id.end_view;
            }
        });
    }

    @Override
    protected void initEvent() {
        //下拉加载监听
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadBooks(null, tag, 0, count, fields);
            }
        });
        //上拉加载更多监听
        mAdater.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadBooks(null, tag, page * count, count, fields);
            }
        });

        mPresenter.loadBooks(null, tag, 0, count, fields);
        //点击事件
        mAdater.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, Object data) {
                BookInfoResponse bookInfo = mBookInfoResponses.get(holder.getLayoutPosition());
                Bundle bundle = new Bundle();
                bundle.putSerializable(BookInfoResponse.getSerialVersionName(), bookInfo);
                ImageView book_iv = holder.getView(R.id.iv_book_img);
                GlideBitmapDrawable drawable = (GlideBitmapDrawable) book_iv.getDrawable();
                Bitmap bitmap;
                if (drawable != null) {
                    bitmap = drawable.getBitmap();
                    bundle.putParcelable("book_img", bitmap);
                }
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtras(bundle);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//android 5.0后才支持次跳转动画
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), holder.getView(R.id.iv_book_img), "book_img");
                    getActivity().startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void showProgress() {
        mSwipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void showToast(String msg) {
        SnUtils.showShort(mRvContent, msg);
        mAdater.setLoadFail();
    }

    @Override
    public void refreshData(BookListResponse result) {
        if (result != null && !result.getBooks().isEmpty()) {
            mAdater.addDataInPosition(0, result.getBooks());
            mAdater.setLoadComplete();
            page++;


        } else {
            mAdater.setLoadEnd();
        }
    }

    @Override
    public void addData(BookListResponse result) {
        if (result != null && !result.getBooks().isEmpty()) {
            mAdater.addMoreData(result.getBooks());
            mAdater.setLoadComplete();
            page++;
        } else {
            mAdater.setLoadEnd();
        }
    }


}
