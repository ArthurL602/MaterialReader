package com.ljb.materialreader.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.BookListAdater;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.bean.response.douban.BookInfoResponse;
import com.ljb.materialreader.bean.response.douban.BookListResponse;
import com.ljb.materialreader.ui.presenter.BookListPresenter;
import com.ljb.materialreader.ui.view.BookListView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.ResourceUtils;
import com.ljb.materialreader.utils.SnUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description : 搜索结果Activity
 */

public class SearchResultActivity extends BaseActivity<BookListPresenter> implements BookListView {

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,"
            + "summary,images,pages,price,binding,isbn13,alt";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private int count = 20;
    private int page = 0;
    private String q;
    private List<BookInfoResponse> mBookInfoResponses;
    private int mSpanCount;
    private GridLayoutManager mLayoutManager;
    private BookListAdater mAdater;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected BookListPresenter getPresenter() {
        return new BookListPresenter(this, this);
    }


    @Override
    protected void initView() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        q = getIntent().getStringExtra("q");
        getSupportActionBar().setTitle(q);

        mBookInfoResponses = new ArrayList<>();
        mSpanCount = ResourceUtils.getInteger(R.integer.home_span_count);
        mAdater = new BookListAdater(mBookInfoResponses, this, mSpanCount);

        mLayoutManager = new GridLayoutManager(this, mSpanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdater.getItemCountSpan(position);
            }
        });
        //添加布局管理器
        mRvContent.setLayoutManager(mLayoutManager);
        //设置适配器
        mRvContent.setAdapter(mAdater);


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
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);


        page = 1;
        mPresenter.loadBooks(q, null, 0, count, fields);
    }

    @Override
    protected void initEvent() {
        //监听刷新
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.loadBooks(q, null, 0, count, fields);
            }
        });
        //监听加载更多
        mAdater.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!mSwipeRefresh.isRefreshing()) {
                    mPresenter.loadBooks(q, null, page * count, count, fields);
                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
//        mSwipeRefresh.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefresh.setRefreshing(true);
//            }
//        });
    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showToast(String msg) {
        SnUtils.showShort(mRvContent, msg);
        mAdater.setLoadFail();
    }

    @Override
    public void refreshData(BookListResponse result) {
        mAdater.addNewData(result.getBooks());
        if (result.getTotal() > page * count) {
            page++;
            mAdater.setLoadComplete();
        } else {//已经加载完了
            mAdater.setLoadEnd();
        }

    }

    @Override
    public void addData(BookListResponse result) {
        mAdater.addMoreData(result.getBooks());
        //判断是否已经加载完所有数据
        if (result.getTotal() > page * count) {
            page++;
            mAdater.setLoadComplete();
        } else {
            mAdater.setLoadEnd();
        }

    }


}
