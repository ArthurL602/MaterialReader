package com.ljb.materialreader.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.ljb.materialreader.ui.activity.MainActivity;
import com.ljb.materialreader.ui.presenter.BookListPresenter;
import com.ljb.materialreader.ui.view.BookListView;
import com.ljb.materialreader.ui.widget.LoadMoreView;
import com.ljb.materialreader.utils.TUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 图书列表界面
 */

public class BookListFragment extends BaseFragment<BookListPresenter> implements BookListView {
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    //接口调用参数：tag：标签；q:搜索关键字；fields:过滤词；count:一次返回数据数
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    //标签
    private String tag = "hot";
    //过滤词
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,"
            + "summary,images,pages,price,binding,isbn13,series,alt";
    //一次返回数据数
    private int count = 20;
    //当前页数
    private int page;

    private List<BookInfoResponse> mBookInfoResponses;
    private GridLayoutManager mGridLayoutManager;
    private BookListAdater mBookListAdater;

    public static BookListFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        BookListFragment bookListFragment = new BookListFragment();
        bookListFragment.setArguments(bundle);
        return bookListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_content;
    }

    @Override
    protected BookListPresenter getPresenter() {
        return new BookListPresenter(this, this);
    }

    @Override
    protected void initView() {
        //获取tag,判断当前booklist是哪个标签列表
        Bundle bundle = getArguments();
        if (bundle != null) {
            String result = bundle.getString("tag");
            if (!TextUtils.isEmpty(result)) {
                tag = result;
            }
        }

        int spanCount = getResources().getInteger(R.integer.home_span_count);
        mBookInfoResponses = new ArrayList<>();
//        mPresenter.loadBooks();

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);
        //设置布局管理器
        mGridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mBookListAdater.getItemCountSpan(position);
            }
        });
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRvContent.setLayoutManager(mGridLayoutManager);
        //设置Adapter
        mBookListAdater = new BookListAdater(mBookInfoResponses, getActivity(), spanCount);
        mRvContent.setAdapter(mBookListAdater);
        //设置添加移除动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        //绑定fab
        mBookListAdater.bindFab(((MainActivity) getActivity()).getFab());
        //加载数据
        mPresenter.loadBooks(null, tag, 0, count, fields);
    }

    @Override
    protected void initEvent() {
        //刷新
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadBooks(null, tag, 0, count, fields);
            }
        });
        //加载更多
        mBookListAdater.setOnLoadMoreListener(new BaseRvAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!mSwipeRefresh.isRefreshing()) {
                    mPresenter.loadBooks(null, tag, page * count, count, fields);
                }
            }
        });
        //设置自定义加载更多View
        mBookListAdater.setLoadMoreView(new LoadMoreView() {
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
        // RecyclerView的点击事件
        mBookListAdater.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<BookInfoResponse>() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, BookInfoResponse data) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(BookInfoResponse.getSerialVersionName(), data);
                ImageView book_iv = holder.getView(R.id.iv_book_img);
                GlideBitmapDrawable drawable = (GlideBitmapDrawable) book_iv.getDrawable();
                Bitmap bitmap;
                if (drawable != null) {
                    bitmap = drawable.getBitmap();
                    bundle.putParcelable("book_img", bitmap);
                }
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtras(bundle);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), holder.getView(R.id.iv_book_img), "book_img");
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
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
        TUtils.showShort(msg);
        mBookListAdater.setLoadFail();
    }


    @Override
    public void refreshData(BookListResponse result) {
        if (result != null && result.getBooks()!=null&&!result.getBooks().isEmpty()) {
            mBookListAdater.addDataInPosition(0, result.getBooks());
            mBookListAdater.setLoadComplete();
            page++;


        } else {
            mBookListAdater.setLoadEnd();
        }

    }

    @Override
    public void addData(BookListResponse result) {
        if (result != null && !result.getBooks().isEmpty()) {
            mBookListAdater.addMoreData(result.getBooks());
            mBookListAdater.setLoadComplete();
            page++;
        } else {
            mBookListAdater.setLoadEnd();
        }
    }
}
