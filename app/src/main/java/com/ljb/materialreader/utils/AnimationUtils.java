package com.ljb.materialreader.utils;

import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :动画工具类
 */

public class AnimationUtils {
    /**
     * 晃动动画
     * @param count 1秒晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int count) {
        TranslateAnimation animation = new TranslateAnimation(0, 10, 0, 0);
        CycleInterpolator cycleInterpolator = new CycleInterpolator(count);
        animation.setDuration(1000);
        animation.setInterpolator(cycleInterpolator);
        return animation;
    }
}
