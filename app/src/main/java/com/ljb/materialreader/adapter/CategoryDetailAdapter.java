package com.ljb.materialreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ljb.materialreader.ui.fragment.CategoryDetailFragment;

import java.util.List;

/**
 * Author      :meloon
 * Date        :2018/1/9
 * Description : 分类详情列表适配器
 */

public class CategoryDetailAdapter extends FragmentStatePagerAdapter {
    private String[] categorys;
    private List<CategoryDetailFragment> mFragments;

    public CategoryDetailAdapter(FragmentManager fm, String[] categorys, List<CategoryDetailFragment> fragments) {
        super(fm);
        this.categorys = categorys;
        mFragments = fragments;
    }

    public CategoryDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categorys[position];
    }
}
