package com.ljb.materialreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ljb.materialreader.app.App;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description : SharePreference工具类
 */

public class SPUtils {
    public static void put(String key, String value) {
        SharedPreferences sp = App.getContext().getSharedPreferences("material_reader", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String get(String key, String def) {
        SharedPreferences sp = App.getContext().getSharedPreferences("material_reader", Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

}
