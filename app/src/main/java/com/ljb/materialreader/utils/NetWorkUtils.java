package com.ljb.materialreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :网络工具类
 */

public class NetWorkUtils {

    /**
     * 判断网络是否连接有效
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }
}
