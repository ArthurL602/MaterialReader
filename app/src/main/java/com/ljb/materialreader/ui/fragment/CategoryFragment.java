package com.ljb.materialreader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.CategoryAdapter;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.base.BaseRvAdapter;
import com.ljb.materialreader.bean.CategoryBean;
import com.ljb.materialreader.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 分类Fragment
 */

public class CategoryFragment extends BaseFragment {

    private List<CategoryBean> mDatas;

    @BindView(R.id.rv)
    RecyclerView mRv;
    private CategoryAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    public static CategoryFragment newInstance() {
        Bundle bundle = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initData();

        mAdapter = new CategoryAdapter(mDatas, getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRv.setLayoutManager(mLayoutManager);
        mRv.setAdapter(mAdapter);

        mRv.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        String[] names = ResourceUtils.getStringArray(R.array.book_category);
        int[] icons = ResourceUtils.getDrawableArray(R.array.book_category_ic);
        for (int i = 0; i < names.length; i++) {
            mDatas.add(new CategoryBean(names[i], icons[i]));
        }
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRvAdapter adapter) {
            }
        });
    }

}
