package com.ljb.materialreader.bean;

/**
 * Author      :ljb
 * Date        :2018/1/3
 * Description :分类实体类
 */

public class CategoryBean {
    private String name;
    private int iconId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public CategoryBean(String name, int iconId) {

        this.name = name;
        this.iconId = iconId;
    }
}
