package com.ljb.materialreader.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ljb.materialreader.base.BaseFragment;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : ViewPager的适配器
 */

public class VpAdater extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;
    private String[] mTitles;
    private Context mContext;

    public VpAdater(FragmentManager fm, List<BaseFragment> fragments, Context context,String [] titles) {
        super(fm);
        mFragments = fragments;
        mTitles =titles;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments != null ? mFragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null ? mTitles[position] : "";
    }


}
