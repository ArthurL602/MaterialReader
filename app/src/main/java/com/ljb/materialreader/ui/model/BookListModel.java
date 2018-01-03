package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IBookListService;
import com.ljb.materialreader.base.BaseModel;
import com.ljb.materialreader.bean.response.douban.BookListResponse;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public class BookListModel extends BaseModel {
    private LifecycleProvider mProvider;
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
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new io.reactivex.Observer<BookListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BookListResponse bookListResponse) {
                        if (bookListResponse != null) {
                            listener.onSuccess(bookListResponse);
                        } else {
                            listener.onFailure();
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
