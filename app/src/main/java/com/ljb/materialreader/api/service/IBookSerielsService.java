package com.ljb.materialreader.api.service;

import com.ljb.materialreader.bean.response.douban.BookSeriesListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description :
 */

public interface IBookSerielsService {
    @GET("book/series/{seriesId}/books")
    Observable<BookSeriesListResponse> getSeriesList(@Path("seriesId") String seriesId, @Query("start") int start,
                                                     @Query("count") int count, @Query("fields") String fields);
}
