package com.ljb.materialreader.base;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public interface BaseView  extends IBaseView{
    void showProgress();

    void hideProgress();

    void showToast(String  msg);
}
