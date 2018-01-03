package com.ljb.materialreader.api.service;


import com.ljb.materialreader.bean.response.douban.BookListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public interface IBookListService {
    @GET("book/search")
    Observable<BookListResponse> getBookList(@Query("q") String q, @Query("tag") String tag, @Query
            ("start") int start, @Query("count") int count, @Query("fields") String fields);
}