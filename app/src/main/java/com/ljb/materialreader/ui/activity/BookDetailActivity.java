package com.ljb.materialreader.ui.activity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.BookDetailAdapter;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseViewHolder;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.bean.response.douban.BookReviewResponse;
import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;
import com.ljb.materialreader.bean.response.douban.BookSeriesListResponse;
import com.ljb.materialreader.ui.presenter.BookDetailPresenter;
import com.ljb.materialreader.ui.view.BookDetailView;
import com.ljb.materialreader.utils.SnUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description : 书籍详细介绍Activity
 */

public class BookDetailActivity extends BaseActivity<BookDetailPresenter> implements BookDetailView {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static final String SERIES_FIELDS = "id,title,subtitle,origin_title,rating,author,translator,publisher,"
            + "pubdate,summary,images,pages,price,binding,isbn13,series";
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 6;
    private static final int PAGE = 0;


    @BindView(R.id.iv_book_bg)
    ImageView mIvBookBg;
    @BindView(R.id.iv_book_img)
    ImageView mIvBookImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarlayout)
    CollapsingToolbarLayout mCollapsingToolbarlayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.topCoordinatorlayout)
    CoordinatorLayout mTopCoordinatorlayout;
    /**
     *
     */
    private BookSeriesListResponse mBookSeriesListResponse;
    /**
     * 图书评论集合
     */
    private BookReviewsListResponse mBookReviewsListResponse;
    private BookInfoResponse mBookInfoResponse;
    private Bitmap mBookImg;
    private BookDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected BookDetailPresenter getPresenter() {
        return new BookDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        initData();
        initToolbar();
        mBookSeriesListResponse = new BookSeriesListResponse();
        mBookReviewsListResponse = new BookReviewsListResponse();

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(layoutManager);

        //设置适配器
        mAdapter = new BookDetailAdapter(mBookInfoResponse, mBookReviewsListResponse, mBookSeriesListResponse);
        mRvContent.setAdapter(mAdapter);

        //设置动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        //设置标题
        mCollapsingToolbarlayout.setTitle(mBookInfoResponse.getTitle());

        //设置图片背景
        if (mBookImg != null) {
            mIvBookBg.setImageBitmap(mBookImg);
//            mIvBookImg.setImageBitmap();
        } else {
            Glide.with(this).load(mBookInfoResponse.getUrl()).into(mIvBookBg);
        }
        //请求数据
        mPresenter.loadReview(mBookInfoResponse.getId(), PAGE * REVIEWS_COUNT, REVIEWS_COUNT, COMMENT_FIELDS);

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home://返回HomeActivity
                finish();
            break;
            case R.id.action_share://分享
                StringBuilder builder = new StringBuilder();
                builder.append("你的好友给你分享了一本书《"+mBookInfoResponse.getTitle()+"》 吼吼!!");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,builder.toString());
                startActivity(Intent.createChooser(intent,"分享"));
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * 获取一系列数据
     */
    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        //图书信息
        mBookInfoResponse = (BookInfoResponse) intent.getSerializableExtra(BookInfoResponse.getSerialVersionName());
        //图书图片
        mBookImg = intent.getParcelableExtra("book_img");
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new BookDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final BaseViewHolder holder, final BookDetailAdapter adapter) {
                switch (holder.getItemViewType()) {
                    case BookDetailAdapter.TYPE_BOOK_INFO:
                        new AlertDialog.Builder(BookDetailActivity.this)//
                                .setTitle("详细信息")//
                                .setMessage(adapter.getBuilder().toString())//
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        ObjectAnimator.ofFloat(holder.getView(R.id.iv_more_info), "rotation", 90, 0)
                                                .start();
                                        adapter.setFlag(false);
                                    }
                                }).show();
                        break;
                }
            }
        });
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String msg) {
        SnUtils.showShort(mRvContent, msg);
    }

    @Override
    public void updateView(Object resutl) {
        if (resutl instanceof BookReviewsListResponse) {
            BookReviewsListResponse reviews = (BookReviewsListResponse) resutl;
            List<BookReviewResponse> review = reviews.getReviews();

            mAdapter.addReviews(review);
            if (mBookInfoResponse.getSeries() != null) {//如果推荐书籍不为空
                mPresenter.loadSerial(mBookInfoResponse.getId(), SERIES_COUNT * PAGE, SERIES_COUNT, SERIES_FIELDS);
            }
        } else if (resutl instanceof BookSeriesListResponse) {
            BookSeriesListResponse series = (BookSeriesListResponse) resutl;
            List<BookInfoResponse> serie = series.getBooks();
            mAdapter.addSeries(serie);
        }
    }
}
