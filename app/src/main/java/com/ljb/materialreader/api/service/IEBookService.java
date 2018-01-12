package com.ljb.materialreader.api.service;

import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.bean.response.ebook.CategoryList;
import com.ljb.materialreader.bean.response.ebook.HotWords;
import com.ljb.materialreader.bean.response.ebook.Rankings;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author      :ljb
 * Date        :2018/1/11
 * Description :
 */

public interface IEBookService {
    @GET("/ranking/{rankingId}")
    Observable<Rankings> getRating(@Path("rankingId") String rankingId);

    /**
     * 获取分类列表
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Observable<CategoryList> getCategoryList();

    /**
     * 获取热搜索词
     *
     * @return
     */
    @GET("/book/hot-word")
    Observable<HotWords> getHotWords();

    /**
     * 书籍查询
     *
     * @param query
     * @param start
     * @param limit
     * @return
     */
    @GET("/book/fuzzy-search")
    Observable<BooksByTag> searchBooks(@Query("query") String query, @Query("start") int start, @Query("limit") int
            limit);

    /**
     * 图书列表通过tag查找
     *
     * @param tags
     * @param start
     * @param limit
     * @return
     */
    @GET("/book/by-tags")
    Observable<BooksByTag> getBooksByTag(@Query("tags") String tags, @Query("start") int start, @Query("limit") int
            limit);
}
