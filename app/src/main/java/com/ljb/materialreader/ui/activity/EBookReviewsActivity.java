package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.EBookReviewsAdapter;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.constant.Constant;
import com.ljb.materialreader.ui.presenter.EBookDetailPresenter;
import com.ljb.materialreader.ui.view.IEBookDetailView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.SnUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description : 更多评论
 */

public class EBookReviewsActivity extends BaseActivity<EBookDetailPresenter> implements IEBookDetailView {
    @BindView(R.id.toobar)
    Toolbar mToobar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private String mBookId;
    private String mBookName;
    private HotReview mHotReview;
    private EBookReviewsAdapter mAdapter;
    private List<HotReview.Reviews> mDatas;

    private String sort = Constant.EBOOK_SORT_UPDATED;
    private static int count = 20;
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ebook_reviews;
    }

    @Override
    protected EBookDetailPresenter getPresenter() {
        return new EBookDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        initData();
        mHotReview = new HotReview();
        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(layoutManager);
        //添加动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        mAdapter = new EBookReviewsAdapter(mDatas, this);
        mRvContent.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(new LoadMoreView() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        Intent intent = getIntent();
        if (intent == null) return;
        mBookId = intent.getStringExtra("bookId");
        mBookName = intent.getStringExtra("bookName");
        mToobar.setTitle(mBookName + "评论");
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void initEvent() {
        //下拉刷新
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mPresenter.getBookReviewsList(mBookId, sort, page * count, count);
            }
        });
        //上拉加载
        mAdapter.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getBookReviewsList(mBookId, sort, page * count, count);
            }
        });
        mPresenter.getBookReviewsList(mBookId, sort, page * count, count);
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
        mAdapter.setLoadFail();
    }

    @Override
    public void updateView(Object resutl) {
        HotReview hotReview = (HotReview) resutl;
        if (page == 0) {
            mAdapter.addNewData(hotReview.getReviews());
        }
        mAdapter.addMoreData(hotReview.getReviews());
        if (hotReview.getReviews().size() >= count) {
            page++;
        } else {
            mAdapter.setLoadEnd();
        }
        mAdapter.setLoadComplete();
    }
}
