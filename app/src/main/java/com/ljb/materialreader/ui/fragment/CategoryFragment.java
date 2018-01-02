package com.ljb.materialreader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;

import butterknife.BindView;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 分类Fragment
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRv;

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

    }

    @Override
    protected void initEvent() {

    }

}
