package com.ljb.materialreader.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description : 单位转化工具类
 */

public class DensityUtil {

    public static int dp2px(float value, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources()
                .getDisplayMetrics());
    }
}
