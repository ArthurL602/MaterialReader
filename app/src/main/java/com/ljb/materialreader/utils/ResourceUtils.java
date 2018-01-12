package com.ljb.materialreader.utils;

import android.content.res.TypedArray;

import com.ljb.materialreader.app.App;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :
 */

public class ResourceUtils {

    public static String[] getStringArray(int resId) {
        return App.getContext().getResources().getStringArray(resId);
    }

    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }

    /**
     * 获取图片数组
     *
     * @param resId
     * @return
     */
    public static int[] getDrawableArray(int resId) {
        TypedArray ar = App.getContext().getResources().obtainTypedArray(resId);
        int[] resIds = new int[ar.length()];
        for (int i = 0; i < resIds.length; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        return resIds;
    }

    public static int getInteger(int resId) {
        return App.getContext().getResources().getInteger(resId);
    }
}
