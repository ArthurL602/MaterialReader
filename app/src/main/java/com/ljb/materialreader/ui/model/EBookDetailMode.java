package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IEBookService;
import com.ljb.materialreader.base.BaseModel;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.bean.response.ebook.LikedBookList;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description :
 */

public class EBookDetailMode extends BaseModel {
    private IEBookService mIEBookService;

    public void getBookDetail(String bookId, final ApiCompleteListener<BookDetail> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getBookDeatail(bookId)//
                .compose(getProvider().bindToLifecycle())//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BookDetail bookDetail) {
                        if (bookDetail != null) {
                            listener.onSuccess(bookDetail);
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


    public void getBookByTags(String tags, int start, final int limit, final ApiCompleteListener<BooksByTag> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getBooksByTag(tags, start, limit)//
                .compose(getProvider().bindToLifecycle())//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<BooksByTag>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BooksByTag booksByTag) {
                        if (booksByTag != null) {
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

    public void getRecommendBookList(String bookId, final int limit, final ApiCompleteListener<LikedBookList>
            listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getRecommenBookList(bookId, limit)//
                .compose(getProvider().bindToLifecycle())//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<LikedBookList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LikedBookList likedBookList) {
                        if (likedBookList != null) {
                            listener.onSuccess(likedBookList);
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

    public void getHotReview(String book, final int limit, final ApiCompleteListener<HotReview> listener) {
        initService();
        mIEBookService.getHotReview(book, limit)//
                .compose(getProvider().bindToLifecycle())//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HotReview hotReview) {
                        if (hotReview != null) {
                            listener.onSuccess(hotReview);

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

    public void getBookReviewsList(String book, String sort, int start, int limit, final
    ApiCompleteListener<HotReview> listener) {
        initService();
        mIEBookService.getBookReviewsList(book, sort, start, limit)//
                .compose(getProvider().bindToLifecycle())//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HotReview hotReview) {
                        if (hotReview != null) {
                            listener.onSuccess(hotReview);
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

    private void initService() {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
    }
}
