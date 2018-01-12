package com.ljb.materialreader.utils;

import com.ljb.materialreader.constant.Constant;
import com.ljb.materialreader.constant.UrlConstant;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description : EBook界面工具类
 */

public class EBookUtils {

    public static String getSex() {
        return SPUtils.get("sex", "man");
    }

    public static void putSex(String sex) {
        SPUtils.put("sex", sex);
    }

    public static String getImageUrl(String name) {
        return UrlConstant.HOST_URL_ZSSQ_IMG + name;
    }

    public static String getRankId(int type, String sex) {
        switch (type) {
            case Constant.TYPE_HOT_RANKING:
                return getHotRankId(sex);
            case Constant.TYPE_RETAINED_RANKING:
                return getRetainedRankId(sex);
            case Constant.TYPE_FINISHED_RANKING:
                return getFinishedRankId(sex);
            case Constant.TYPE_POTENTIAL_RANKING:
                return getPotEntialRankId(sex);
        }
        return getHotRankId(sex);
    }

    private static String getPotEntialRankId(String sex) {
        switch (sex) {
            case "man":
                return Constant.EBOOK_RANK_ID_POTENTIAL_MALE;
            case "women":
                return Constant.EBOOK_RANK_ID_POTENTIAL_FEMALE;
            default:
                return Constant.EBOOK_RANK_ID_POTENTIAL_MALE;
        }
    }

    private static String getFinishedRankId(String sex) {
        switch (sex) {
            case "man":
                return Constant.EBOOK_RANK_ID_FINISHED_MALE;
            case "women":
                return Constant.EBOOK_RANK_ID_FINISHED_FEMALE;
            default:
                return Constant.EBOOK_RANK_ID_FINISHED_MALE;
        }
    }

    private static String getRetainedRankId(String sex) {
        switch (sex) {
            case "man":
                return Constant.EBOOK_RANK_ID_RETAINED_MALE;
            case "women":
                return Constant.EBOOK_RANK_ID_RETAINED_FEMALE;
            default:
                return Constant.EBOOK_RANK_ID_RETAINED_MALE;
        }
    }

    private static String getHotRankId(String sex) {
        switch (sex) {
            case "man":
                return Constant.EBOOK_RANK_ID_HOT_MALE;
            case "women":
                return Constant.EBOOK_RANK_ID_HOT_FEMALE;
            default:
                return Constant.EBOOK_RANK_ID_HOT_MALE;
        }
    }
}
