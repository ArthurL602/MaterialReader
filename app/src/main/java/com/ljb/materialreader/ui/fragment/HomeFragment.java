package com.ljb.materialreader.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.VpAdater;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 首页Fragment
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private List<BaseFragment> mFragments;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        //绑定toolbar
        ((MainActivity) getActivity()).setToolbar(mToolbar);
        //绑定fab
        ((MainActivity) getActivity()).setFab(mFab);
        init();
    }

    private void init() {
        mFragments = new ArrayList<>();
        mFragments.add(BookListFragment.newInstance("新书"));
        mFragments.add(BookListFragment.newInstance("热门"));
        mFragments.add(BookListFragment.newInstance("推荐"));
        mFragments.add(CategoryFragment.newInstance());
        mFragments.add(BookListFragment.newInstance("小说"));
        mFragments.add(DiscoverFragment.newInstance(0));

        mVp.setAdapter(new VpAdater(getChildFragmentManager(), mFragments, getContext()));
        //设置预加载
        mVp.setOffscreenPageLimit(5);
        mVp.setCurrentItem(2);
        //tablayout和viewpager进行绑定
        mTab.setupWithViewPager(mVp);

        mTab.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));

    }

    @Override
    protected void initEvent() {

    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
    }
}
