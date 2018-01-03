package com.ljb.materialreader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :所有个Activity的基类，处理Activity的共性内容和逻辑
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initEvent();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.dettachView();
        }
    }

    protected abstract int getLayoutId();

    protected abstract P getPresenter();

    protected abstract void initView();

    protected abstract void initEvent();
}
