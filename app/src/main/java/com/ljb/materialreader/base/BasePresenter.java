package com.ljb.materialreader.base;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 所有Presenter的基类
 */

public class BasePresenter<V extends IBaseView> {
    private V mView;

    public void attachView(V view) {
        mView = view;
    }

    public void dettachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }


}
