package com.ljb.materialreader.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ljb.materialreader.R;
import com.ljb.materialreader.adapter.CategoryDetailAdapter;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.constant.Constant;
import com.ljb.materialreader.ui.fragment.CategoryDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author      :meloon
 * Date        :2018/1/9
 * Description : 分类详细介绍界面
 */

public class CategoryDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    private String[] mCategory;
    private List<CategoryDetailFragment> mFragments;
    private CategoryDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_detail;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        mFragments = new ArrayList<>();
        for (String category : mCategory) {
            CategoryDetailFragment fragment = CategoryDetailFragment.newInstance(category);
            mFragments.add(fragment);
        }
        mVp.setOffscreenPageLimit(0);

        mAdapter = new CategoryDetailAdapter(getSupportFragmentManager(), mCategory, mFragments);
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(0);
        mTablayout.setupWithViewPager(mVp);
        mTablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        String title = intent.getStringExtra("title");
        int index = intent.getIntExtra("index", -1);
        getSupportActionBar().setTitle(title);

        switch (index) {
            case Constant.CATEGORY_LITERATURE:
                mCategory = getResources().getStringArray(R.array.book_category_literature);
                break;
            case Constant.CATEGORY_POPULAR:
                mCategory = getResources().getStringArray(R.array.book_category_popular);
                break;
            case Constant.CATEGORY_CULTURE:
                mCategory = getResources().getStringArray(R.array.book_category_culture);
                break;
            case Constant.CATEGORY_LIFE:
                mCategory = getResources().getStringArray(R.array.book_category_life);
                break;
            case Constant.CATEGORY_MANAGEMENT:
                mCategory = getResources().getStringArray(R.array.book_category_management);
                break;
            case Constant.CATEGORY_TECHNOLOGY:
                mCategory = getResources().getStringArray(R.array.book_category_technology);
                break;
            case Constant.CATEGORY_COUNTRY:
                mCategory = getResources().getStringArray(R.array.book_category_country);
                break;
            case Constant.CATEGORY_SUBJECT:
                mCategory = getResources().getStringArray(R.array.book_category_subject);
                break;
            case Constant.CATEGORY_AUTHOR:
                mCategory = getResources().getStringArray(R.array.book_category_author);
                break;
            case Constant.CATEGORY_PUBLISHER:
                mCategory = getResources().getStringArray(R.array.book_category_publisher);
                break;
            case Constant.CATEGORY_THRONG:
                mCategory = getResources().getStringArray(R.array.book_category_throng);
                break;
            case Constant.CATEGORY_RELIGION:
                mCategory = getResources().getStringArray(R.array.book_category_religion);
                break;
            case Constant.CATEGORY_OTHER:
                mCategory = getResources().getStringArray(R.array.book_category_other);
                break;
            default:
                mCategory = new String[]{};
                break;
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }

    @Override
    protected void initEvent() {

    }

}
