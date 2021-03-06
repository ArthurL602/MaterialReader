package com.ljb.materialreader.bean.response.douban;

import java.util.ArrayList;
import java.util.List;

/**
 *  Author      :ljb
 *  Date        :2018/1/4
 *  Description : 图书评论集合实体类
 */

public class BookReviewsListResponse extends BaseResponse {
    private int count;
    private int start;
    private int total;
    private List<BookReviewResponse> reviews;

    public BookReviewsListResponse() {
        this.reviews = new ArrayList<>();
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

    public List<BookReviewResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewResponse> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "BookReviewsListResponse{" + "count=" + count + ", start=" + start + ", total=" + total + ", reviews="
                + reviews + '}';
    }
}
