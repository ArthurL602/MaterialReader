package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.EBookListAdater;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.ui.presenter.ESearchResultPresent;
import com.ljb.materialreader.ui.view.ESearchResultView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.ResourceUtils;
import com.ljb.materialreader.utils.SnUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/12
 * Description : EBook搜索界面
 */

public class ESearchResultActivity extends BaseActivity<ESearchResultPresent> implements ESearchResultView {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final int PRO_LOADING_SIZE = 2;//上滑加载提前N个item开始加载更多数据(暂时有bug)
    private static int PAGE_SIZE = 20;
    private int page = 0;
    private String q;
    private int spanCount = 1;
    private int type;//0:查书，1：查分类

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private List<BookDetail> mBookDetails;
    private EBookListAdater mAdater;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected ESearchResultPresent getPresenter() {
        return new ESearchResultPresent(this, this);
    }

    @Override
    protected void initView() {
        initData();
        initToolbar();
        spanCount = ResourceUtils.getInteger(R.integer.home_span_count);

        mBookDetails = new ArrayList<>();

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(layoutManager);
        //设置适配器
        mAdater = new EBookListAdater(mBookDetails, this, spanCount);
        mRvContent.setAdapter(mAdater);
        //添加默认动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());


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
        //请求数据

        if (type == 0) {
            mPresenter.searchBooks(q, 0 * PAGE_SIZE, PAGE_SIZE);
        } else {
            mPresenter.getBooksByTag(q, 0 * PAGE_SIZE, PAGE_SIZE);
        }

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        Intent intent = getIntent();
        if (intent == null) return;
        type = intent.getIntExtra("type", 0);
        q = intent.getStringExtra("q");
        mToolbar.setTitle(q);
    }

    @Override
    protected void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (type == 0) {
                    mPresenter.searchBooks(q, 0 * PAGE_SIZE, PAGE_SIZE);
                } else {
                    mPresenter.getBooksByTag(q, 0 * PAGE_SIZE, PAGE_SIZE);
                }
            }
        });

        mAdater.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (type == 0) {
                    mPresenter.searchBooks(q, page * PAGE_SIZE, PAGE_SIZE);
                } else {
                    mPresenter.getBooksByTag(q, page * PAGE_SIZE, PAGE_SIZE);
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
    public void onRefreshData(Object data) {
        if (data instanceof BooksByTag) {
            mAdater.addNewData(((BooksByTag) data).getBooks());
            if (((BooksByTag) data).getBooks().size() < PAGE_SIZE) {
                mAdater.setLoadEnd();
            } else {
                mAdater.setLoadComplete();
                page++;
            }
        }
    }

    @Override
    public void onLoadMoreData(Object data) {
        if (data instanceof BooksByTag) {
            mAdater.addMoreData(((BooksByTag) data).getBooks());
            if (((BooksByTag) data).getBooks().size() < PAGE_SIZE) {
                mAdater.setLoadEnd();
            } else {
                mAdater.setLoadComplete();
                page++;
            }
        }
    }
}
