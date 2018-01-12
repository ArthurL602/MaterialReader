package com.ljb.materialreader.ui.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.ljb.materialreader.R;
import com.ljb.materialreader.base.BaseActivity;
import com.ljb.materialreader.base.BaseFragment;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.control.SearchViewController;
import com.ljb.materialreader.ui.fragment.EBookFragment;
import com.ljb.materialreader.ui.fragment.HomeFragment;
import com.ljb.materialreader.utils.KeyBoardUtils;
import com.ljb.materialreader.utils.ScreenUtils;
import com.ljb.materialreader.utils.SnUtils;
import com.ljb.materialreader.utils.StatusUtils;
import com.ljb.materialreader.utils.TUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.ljb.materialreader.R.id.drawer;

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
    @BindView(drawer)
    DrawerLayout mDrawerView;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private Toolbar mToolbar;
    private PopupWindow mPopupWindow;
    private SearchViewController mController;
    private BaseFragment mCurrentFragment;
    private RxPermissions mRxPermissions;
    private long lastMillis;
    private EBookFragment mEBookFragment;

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
        StatusUtils.setDrawerLayoutStatus(this, mDrawerView, false, getResources().getColor(R.color.colorPrimary));
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, HomeFragment.newInstance());
        transaction.commit();

        mRxPermissions = new RxPermissions(this);
    }

    protected BaseFragment getCurrrentFrament() {
        return mCurrentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
    }

    private void initFragment() {
//        mHomeFragment = HomeFragment.newInstance();
//        mEBookFragment = EBookFragment.newInstance();
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

    /**
     * 显示搜索框
     */
    public void showSearchView() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (mPopupWindow == null) {
            mController = new SearchViewController(this, new SearchViewController.OnSearchHandlerListener() {
                @Override
                public void onSearch(int code) {
                    switch (code) {
                        case SearchViewController.RESULT_SEARCH_EMPTY_KEYWORD://空
                            SnUtils.showShort(mDrawerView, "搜索关键字不能为空");
                            break;
                        case SearchViewController.RESULT_SEARCH_CANCEL://返回
                            mPopupWindow.dismiss();
                            break;
                        case SearchViewController.RESULT_SEARCH_GO_SCAN://扫描
                            mRxPermissions.request(Manifest.permission.CAMERA)//
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                                                KeyBoardUtils.closeKeyBord(mController.getEtContent(), MainActivity
                                                        .this);
                                            } else {
                                                TUtils.showShort("请授予权限");
                                            }
                                        }
                                    });

                            break;
                        case SearchViewController.RESULT_SEARCH_SEARCH://搜索
                            String request = mController.getEtContent().getText().toString();
                            Intent intent = null;
                            if (getCurrrentFrament() instanceof HomeFragment) {//如果当前是HomeFragment
                                intent = new Intent(MainActivity.this, SearchResultActivity.class);
                            }
                            intent.putExtra("q", request);
                            startActivity(intent);
                            break;
                    }

                }
            });
        }
        mPopupWindow = new PopupWindow(mController.getContentView(), WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        //监听popupwindow消失
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //输入的清空
                mController.getEtContent().setText("");
                //隐藏软键盘
                KeyBoardUtils.closeKeyBord(mController.getEtContent(), MainActivity.this);
                //添加消失动画
                ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1.0f);
                animator.setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        lp.alpha = (float) animation.getAnimatedValue();
                        lp.dimAmount = (float) animation.getAnimatedValue();
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    }
                });
                animator.start();
            }
        });
        //打开软键盘
        KeyBoardUtils.openKeyBord(mController.getEtContent(), this);
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                lp.dimAmount = (float) animation.getAnimatedValue();
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        mPopupWindow.showAtLocation(mDrawerView, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(this));
        animator.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home://home
                switchFragment(HomeFragment.newInstance());
                break;
            case R.id.nav_ebook://ebook
                switchFragment(EBookFragment.newInstance());
                break;
            case R.id.nav_bookshelf:
                break;
            case R.id.nav_manager:
                break;
            case R.id.nav_theme:
                break;
            case R.id.nav_send:
                break;
        }
        mDrawerView.closeDrawer(Gravity.START);
        return true;
    }

    private void switchFragment(Fragment to) {
        BaseFragment fragment = getCurrrentFrament();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_content, to);
//        if (to.isAdded()) {//判断是否被添加了
//            Log.e("TAG", "show");
//            transaction.hide(fragment).show(to);
//        } else {
//            Log.e("TAG", "add");
//            transaction.add(R.id.frame_content, to);
//        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = R.menu.menu_main;
        if (getCurrrentFrament() instanceof HomeFragment) {
            menuId = R.menu.menu_main;
        }
        if (getCurrrentFrament() instanceof EBookFragment) {
            menuId = R.menu.ebook_main;
        }
        getMenuInflater().inflate(menuId, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                showSearchView();
                return true;
        }
        getCurrrentFrament().onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerView.isDrawerOpen(Gravity.START)) {
            mDrawerView.closeDrawer(Gravity.START);
        } else {
            long currentMillis = System.currentTimeMillis();
            if (currentMillis - lastMillis > 500) {
                SnUtils.showAction(mNavView, "真的要退出嘛？", "确定退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else {
                finish();
            }
            lastMillis = currentMillis;
        }

    }
}
