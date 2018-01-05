package com.ljb.materialreader.bean.response.douban;

import java.util.List;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description : 图书信息集合实例类
 */

public class BookListResponse extends BaseResponse {
    private int count;
    private int start;
    private int total;
    protected List<BookInfoResponse> books;

    public BookListResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookInfoResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookInfoResponse> books) {
        this.books = books;
    }
}
