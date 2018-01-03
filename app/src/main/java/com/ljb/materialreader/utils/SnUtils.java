package com.ljb.materialreader.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :snackbar工具类
 */

public class SnUtils {
    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();

    }
}
