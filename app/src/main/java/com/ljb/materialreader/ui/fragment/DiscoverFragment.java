package com.ljb.materialreader.ui.fragment;

import android.os.Bundle;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :发现Fragment
 */

public class DiscoverFragment extends BaseFragment {


    public static DiscoverFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
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
