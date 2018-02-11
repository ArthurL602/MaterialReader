package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.EBookDetailAdapter;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.bean.response.ebook.LikedBookList;
import com.ljb.materialreader.ui.presenter.EBookDetailPresenter;
import com.ljb.materialreader.ui.view.IEBookDetailView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description : 电子书详情页
 */

public class EBookDetailActivity extends BaseActivity<EBookDetailPresenter> implements IEBookDetailView {

    @BindView(R.id.iv_book_bg)
    ImageView mIvBookBg;
    @BindView(R.id.iv_book_img)
    ImageView mIvBookImg;
    @BindView(R.id.toobar)
    Toolbar mToobar;
    @BindView(R.id.collapsingToolbarlayout)
    CollapsingToolbarLayout mCollapsingToolbarlayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.topCoordinatorlayout)
    CoordinatorLayout mTopCoordinatorlayout;
    private BookDetail mBookDetail;
    private String mBookId;
    private HotReview mHotReview;
    private LikedBookList mLikedBookList;
    private BooksByTag mBooksByTag;
    private EBookDetailAdapter mAdapter;

    private static final int PAGE = 0;
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 8;
    private static final int BOOK_LIST_COUNT = 4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ebook_detail;
    }

    @Override
    protected EBookDetailPresenter getPresenter() {
        return new EBookDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        initData();
        mHotReview = new HotReview();
        mLikedBookList = new LikedBookList();
        mBooksByTag = new BooksByTag();

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(layoutManager);

        //添加动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        //设置适配器
        mAdapter = new EBookDetailAdapter(this, mBookDetail, mHotReview, mLikedBookList, mBooksByTag);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        if (!TextUtils.isEmpty(mBookId)) {
            mPresenter.getBookDetail(mBookId);
            mPresenter.getHotReviews(mBookId,REVIEWS_COUNT);
        }

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        mBookDetail = intent.getExtras().getParcelable("BookDetail");
        mBookId = intent.getExtras().getString("bookId");
        Bitmap bitmap = intent.getExtras().getParcelable("book_img");
        mToobar.setTitle(mBookDetail.getTitle());
        mIvBookBg.setImageBitmap(bitmap);

        setSupportActionBar(mToobar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:

                StringBuilder sb = new StringBuilder();
                sb.append("你的好友给你分享了一本好书《");

                sb.append(mBookDetail.getTitle());
                sb.append("》 ，吼吼！");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(shareIntent, "分享"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (item.getIcon() instanceof Animatable) {
                        ((Animatable) item.getIcon()).start();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    }

    @Override
    public void updateView(Object resutl) {
        if (resutl instanceof BookDetail) {
            mBookDetail = (BookDetail) resutl;
            mAdapter.setBookDetail(mBookDetail);
            mAdapter.notifyItemChanged(0);
            if (mBookDetail.getTags().size() > 0) {
                mPresenter.getBookTag(mBookDetail.getTags().get(0), PAGE, SERIES_COUNT);
            }
            mPresenter.getRecommendBookList(mBookId, BOOK_LIST_COUNT);
        } else if (resutl instanceof BooksByTag) {
            BooksByTag booksByTag = (BooksByTag) resutl;
            mBooksByTag.addBooks(booksByTag.getBooks());
            mAdapter.notifyItemChanged(mHotReview.getReviews().size() + 2);
        }else if(resutl instanceof  LikedBookList){
            LikedBookList  likedBookList = (LikedBookList) resutl;
            mLikedBookList.addBookList(likedBookList.getBookList());
            final int start = mHotReview.getReviews().size() + 2;
            mAdapter.notifyItemRangeInserted(start + 1, start + mLikedBookList.getBookList().size());
        }else if(resutl instanceof  HotReview){
            HotReview hotReview   = (HotReview) resutl;
            mHotReview.getReviews().clear();
            mHotReview.getReviews().addAll(hotReview.getReviews());
            mAdapter.notifyItemChanged(1);
        }
    }
}
