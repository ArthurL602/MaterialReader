package com.ljb.materialreader.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.ui.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :首页
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.frame_content)
    FrameLayout mFrameContent;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer)
    DrawerLayout mDrawerView;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private Toolbar mToolbar;

    public FloatingActionButton getFab() {
        return mFab;
    }

    private FloatingActionButton mFab;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initFragment();
        Log.e("TAG", "initView");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.frame_content, mHomeFragment);
        transaction.commit();
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
    }

    @Override
    protected void initEvent() {

    }


    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerView, mToolbar, R.string
                    .nav_drawer_open, R.string.nav_drawer_close);
            mDrawerView.addDrawerListener(toggle);
            //同步状态
            toggle.syncState();
            mNavView.setNavigationItemSelectedListener(this);
        }
    }

    public void setFab(FloatingActionButton fab) {
        mFab = fab;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @OnClick(R.id.nav_view)
    public void onViewClicked() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
