package com.ljb.materialreader.ui.model;

import com.ljb.materialreader.api.ServiceFactory;
import com.ljb.materialreader.api.service.IEBookService;
import com.ljb.materialreader.base.BaseModel;
import com.ljb.materialreader.bean.response.ebook.CategoryList;
import com.ljb.materialreader.bean.response.ebook.HotWords;
import com.ljb.materialreader.bean.response.ebook.Rankings;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.constant.UrlConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description :
 */

public class EBookListModel extends BaseModel {
    IEBookService mIEBookService;

    public void getRating(final String rankingId, final ApiCompleteListener<Rankings> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getRating(rankingId)//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<Rankings>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Rankings rankings) {
                        if (rankings.isOk()) {
                            listener.onSuccess(rankings);

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

    public void getCategoryList(final ApiCompleteListener<CategoryList> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getCategoryList()//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<CategoryList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryList categoryList) {
                        if (categoryList.isOk()) {
                            listener.onSuccess(categoryList);
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

    public void getHotWords(final ApiCompleteListener<HotWords> listener) {
        if (mIEBookService == null) {
            mIEBookService = ServiceFactory.createService(UrlConstant.HOST_URL_ZSSQ, IEBookService.class);
        }
        mIEBookService.getHotWords()//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .compose(getProvider().bindToLifecycle())//
                .subscribe(new Observer<HotWords>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HotWords hotWords) {
                        if (hotWords.isOk()) {
                            listener.onSuccess(hotWords);
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
