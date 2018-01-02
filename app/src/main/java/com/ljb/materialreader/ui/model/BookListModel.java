package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IBookListService;
import com.ljb.materialreader.bean.response.douban.BookListResponse;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public class BookListModel {
    /**
     * 获取图书列表
     *
     * @param q
     * @param tag
     * @param start
     * @param count
     * @param fields
     */
    public void loadBookList(String q, final String tag, int start, int count, String fields, final
    ApiCompleteListener<BookListResponse> listener) {
        IBookListService iBookListService = ServiceFactory.createService(UrlConstant.HOST_URL_DOUBAN,
                IBookListService.class);
        iBookListService.getBookList(q, tag, start, count, fields)//

                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<BookListResponse>() {
                    @Override
                    public void onCompleted() {
                        listener.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(BookListResponse bookListResponse) {
                        if (bookListResponse != null) {
                            listener.onSuccess(bookListResponse);
                        } else {
                            listener.onFailure();
                        }
                    }
                });

    }
}
