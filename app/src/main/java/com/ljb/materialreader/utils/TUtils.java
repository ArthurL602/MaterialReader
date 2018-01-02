package com.ljb.materialreader.utils;

import android.widget.Toast;

import com.ljb.materialreader.app.App;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :吐司工具类
 */

public class TUtils {

    public static  void showShort(String msg){
        Toast.makeText(App.getContext(),msg,Toast.LENGTH_SHORT).show();
    }
    public static  void showLong(String msg){
        Toast.makeText(App.getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
