package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IBookReviewsService;
import com.ljb.materialreader.api.service.IBookSerielsService;
import com.ljb.materialreader.base.BaseModel;
import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;
import com.ljb.materialreader.bean.response.douban.BookSeriesListResponse;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description : 获取图书详情页数据
 */

public class BookDetailMode extends BaseModel {

    /**
     * 获取图书评论
     */
    public void loadReViewsList(String bookId, int start, int count, String fields, final
    ApiCompleteListener<BookReviewsListResponse> listener) {
        IBookReviewsService iBookReviewsService = ServiceFactory.createService(UrlConstant.HOST_URL_DOUBAN,
                IBookReviewsService.class);
        iBookReviewsService.getBookReviews(bookId, start, count, fields)//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<BookReviewsListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BookReviewsListResponse bookReviewsListResponse) {
                        if (bookReviewsListResponse == null) {
                            listener.onFailure();
                        } else {
                            listener.onSuccess(bookReviewsListResponse);
                        }
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

    }

    /**
     * 获取推荐丛书
     */
    public void loadSeriesList(final String bookId, int start, int count, String fields, final
    ApiCompleteListener<BookSeriesListResponse> listener) {
        IBookSerielsService iBookSerielsService = ServiceFactory.createService(UrlConstant.HOST_URL_DOUBAN,
                IBookSerielsService.class);
        iBookSerielsService.getSeriesList(bookId, start, count, fields)//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<BookSeriesListResponse>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BookSeriesListResponse bookSeriesListResponse) {
                        if (bookSeriesListResponse == null) {
                            listener.onFailure();
                        } else {
                            listener.onSuccess(bookSeriesListResponse);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
    }
}
