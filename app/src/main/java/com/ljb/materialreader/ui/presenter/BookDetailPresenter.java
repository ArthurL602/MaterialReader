package com.ljb.materialreader.ui.presenter;

import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.response.douban.BookReviewsListResponse;
import com.ljb.materialreader.bean.response.douban.BookSeriesListResponse;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.ui.model.BookDetailMode;
import com.ljb.materialreader.ui.view.BookDetailView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Author      :ljb
 * Date        :2018/1/4
 * Description :
 */

public class BookDetailPresenter extends BasePresenter<BookDetailView> {
    private BookDetailView mBookDetailView;
    private BookDetailMode mBookDetailMode;

    public BookDetailPresenter(BookDetailView bookDetailView, LifecycleProvider provider) {
        mBookDetailView = bookDetailView;
        mBookDetailMode = new BookDetailMode();
        mBookDetailMode.setProvider(provider);
    }

    public void loadSerial(String boodId,int start,int count ,String fields) {
        if(!isViewAttached()) return;
        mBookDetailView.showProgress();
        mBookDetailMode.loadSeriesList(boodId, start, count, fields, new ApiCompleteListener<BookSeriesListResponse>() {
            @Override
            public void onSuccess(BookSeriesListResponse response) {
                if(!isViewAttached())return;
                mBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if(!isViewAttached())return;
                mBookDetailView.hideProgress();
                mBookDetailView.showToast("请求失败");
            }

            @Override
            public void onError(String error) {
                if(!isViewAttached())return;
                mBookDetailView.hideProgress();
                mBookDetailView.showToast(error);
            }

            @Override
            public void onComplete() {
                if(!isViewAttached())return;
                mBookDetailView.hideProgress();
            }
        });
    }

    public void loadReview(String bookId, int start, int count, String fields) {
        if (!isViewAttached()) {
            return;
        }
        mBookDetailView.showProgress();
        mBookDetailMode.loadReViewsList(bookId, start, count, fields, new
                ApiCompleteListener<BookReviewsListResponse>() {


            @Override
            public void onSuccess(BookReviewsListResponse response) {
                if (!isViewAttached()) {
                    return;
                }
                mBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) {
                    return;
                }
                mBookDetailView.hideProgress();
                mBookDetailView.showToast("请求失败");
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) {
                    return;
                }
                mBookDetailView.hideProgress();
                mBookDetailView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) {
                    return;
                }
                mBookDetailView.hideProgress();
            }
        });
    }
}
