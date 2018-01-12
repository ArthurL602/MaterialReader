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

    public static void showAction(View view, String content, String actionStr, final View.OnClickListener callback) {
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).setAction(actionStr, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(v);
            }
        }).show();
    }
}
