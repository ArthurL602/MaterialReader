package com.ljb.materialreader.api.service;

import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description : 获取评论
 */

public interface IBookReviewsService {
    @GET("book/{bookId}/reviews")
    Observable<BookReviewsListResponse> getBookReviews(@Path("bookId") String bookId, @Query("start") int start,
                                                       @Query("count") int count, @Query("fields") String fields);
}
