package com.ljb.materialreader.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.EBookCategoryAdapter;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.bean.response.ebook.CategoryList;
import com.ljb.materialreader.ui.presenter.EBookPresenter;
import com.ljb.materialreader.ui.view.EBookListView;
import com.ljb.materialreader.utils.ResourceUtils;
import com.ljb.materialreader.utils.SnUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      : ljb
 * Date        :2018/1/12
 * Description : EBook分类列表
 */

public class EBookCategoryFragment extends BaseFragment<EBookPresenter> implements EBookListView {


    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private List<CategoryList.CategoryBean> mMans;
    private List<CategoryList.CategoryBean> mWomens;
    private EBookCategoryAdapter mAdapter;

    public static EBookCategoryFragment newInstance() {
        EBookCategoryFragment fragment = new EBookCategoryFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_content;
    }

    @Override
    protected EBookPresenter getPresenter() {
        return new EBookPresenter(this, this);
    }

    @Override
    protected void initView() {
        mMans = new ArrayList<>();
        mWomens = new ArrayList<>();

        final int spant = ResourceUtils.getInteger(R.integer.category_span_count);
        //设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == mMans.size() + 1) {
                    return spant;
                } else {
                    return 1;
                }
            }
        });
        mRvContent.setLayoutManager(layoutManager);
        //设置适配器
        mAdapter = new EBookCategoryAdapter(mMans, mWomens, getContext());
        mRvContent.setAdapter(mAdapter);
        //添加默认动画
        mRvContent.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefresh.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2, R.color
                .recycler_color3, R.color.recycler_color4);
        mPresenter.getCategoryList();
    }

    @Override
    protected void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getCategoryList();
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
    }

    @Override
    public void onRefresh(Object data) {
        if (data instanceof CategoryList) {
            mAdapter.addNewData(((CategoryList) data).getMale(), ((CategoryList) data).getFemale());
        }
    }
}
