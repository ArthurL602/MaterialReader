package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.BookReviewsAdapter;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.bean.response.douban.BookReviewResponse;
import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;
import com.ljb.materialreader.ui.presenter.BookDetailPresenter;
import com.ljb.materialreader.ui.view.BookDetailView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.SnUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/5
 * Description : 评论界面
 */

public class BookReviewsActivity extends BaseActivity<BookDetailPresenter> implements BookDetailView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager mLayoutManager;
    private BookReviewsListResponse mBookReviewsListResponse;
    private BookReviewsAdapter mAdapter;
    private String mBookId;

    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static int count = 20;
    private int page = 0;
    private static String bookId;
    private static String bookName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_reviews;
    }

    @Override
    protected BookDetailPresenter getPresenter() {
        return new BookDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        initToolbar();
        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(mLayoutManager);

        //
        mBookReviewsListResponse = new BookReviewsListResponse();
        //设置适配器
        mAdapter = new BookReviewsAdapter(mBookReviewsListResponse.getReviews(), this);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        //加载数据
        mPresenter.loadReview(mBookId, page * count, count, COMMENT_FIELDS);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        if (intent == null) return;
        String bookName = intent.getStringExtra("bookName");
        mBookId = intent.getStringExtra("bookId");
        if (bookName.isEmpty()) return;
        getSupportActionBar().setTitle(bookName + " 评论");
    }

    @Override
    protected void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //加载数据
                page = 0;
                mPresenter.loadReview(mBookId, page * count, count, COMMENT_FIELDS);
            }
        });

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

        mAdapter.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadReview(mBookId, page * count, count, COMMENT_FIELDS);
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {
        SnUtils.showShort(mRvContent, msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void updateView(Object resutl) {
        BookReviewsListResponse bookReviewsListResponse = (BookReviewsListResponse) resutl;
        List<BookReviewResponse> reviews = bookReviewsListResponse.getReviews();
        if (reviews == null && reviews.isEmpty()) {
            mAdapter.setLoadEnd();
            return;
        }
        if (page == 0) {
            mAdapter.addNewData(reviews);
        } else {
            mAdapter.addLoadMoreData(reviews);
        }
        mAdapter.setLoadComplete();
        if (reviews.size() >= count) {
            page++;
        } else {
            mAdapter.setLoadEnd();
        }


    }
}
