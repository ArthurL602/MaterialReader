package com.ljb.materialreader.bean.response.douban;

import java.util.ArrayList;

/**
 *  Author      :ljb
 *  Date        :2018/1/4
 *  Description : 推荐的书集合实体类
 */

public class BookSeriesListResponse extends BookListResponse {
    public BookSeriesListResponse() {
        this.books = new ArrayList<>();
    }
}
