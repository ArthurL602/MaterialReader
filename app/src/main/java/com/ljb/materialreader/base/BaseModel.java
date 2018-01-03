package com.ljb.materialreader.base;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :
 */

public class BaseModel {
    private LifecycleProvider mProvider;

    public void setProvider(LifecycleProvider provider) {
        mProvider = provider;
    }

    protected  LifecycleProvider getProvider() {
        return mProvider;
    }
}
