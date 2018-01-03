package com.ljb.materialreader.utils;

import android.content.Context;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :屏幕工具类
 */

public class ScreenUtils {
    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight - 8;
    }
}
