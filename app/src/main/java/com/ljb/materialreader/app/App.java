package com.ljb.materialreader.app;

import android.app.Application;
import android.content.Context;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 全局Applicaiont
 */

public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context getContext() {
        return mApp;
    }
}
