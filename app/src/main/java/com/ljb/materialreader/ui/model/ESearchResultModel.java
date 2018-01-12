package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IEBookService;
import com.ljb.materialreader.base.BaseModel;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      :meloon
 * Date        :2018/1/12
 * Description :
 */

public class ESearchResultModel extends BaseModel {

    private IEBookService mIEBookService;

    /**
     * 查询书籍
     *
     * @param query
     * @param start
     * @param limit
     * @param listener
     */
    public void searchBooks(String query, int start, final int limit, final ApiCompleteListener<BooksByTag> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.searchBooks(query, start, limit)//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<BooksByTag>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BooksByTag booksByTag) {
                        if (booksByTag.isOk()) {
                            listener.onSuccess(booksByTag);
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

    public void getBooksByTag(String tags, int start, int limit, final ApiCompleteListener<BooksByTag> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getBooksByTag(tags, start, limit)//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<BooksByTag>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BooksByTag booksByTag) {
                        if (booksByTag.isOk()) {
                            listener.onSuccess(booksByTag);
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
