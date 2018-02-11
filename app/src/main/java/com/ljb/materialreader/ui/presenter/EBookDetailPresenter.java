package com.ljb.materialreader.ui.presenter;

import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.response.ebook.BookDetail;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.bean.response.ebook.HotReview;
import com.ljb.materialreader.bean.response.ebook.LikedBookList;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.ui.model.EBookDetailMode;
import com.ljb.materialreader.ui.view.IEBookDetailView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Author      :meloon
 * Date        :2018/1/29
 * Description :
 */

public class EBookDetailPresenter extends BasePresenter<IEBookDetailView> {

    private EBookDetailMode mEBookDetailMode;
    private IEBookDetailView mIEBookDetailView;

    public EBookDetailPresenter(IEBookDetailView IEBookDetailView, LifecycleProvider provider) {
        mIEBookDetailView = IEBookDetailView;
        mEBookDetailMode = new EBookDetailMode();
        mEBookDetailMode.setProvider(provider);
    }

    public void getBookDetail(String boookId) {
        if (!isViewAttached()) return;
        mIEBookDetailView.showProgress();
        mEBookDetailMode.getBookDetail(boookId, new ApiCompleteListener<BookDetail>() {
            @Override
            public void onSuccess(BookDetail response) {
                if (!isViewAttached()) return;
                mIEBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast("获取数据失败");
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast(error);
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mIEBookDetailView.hideProgress();
            }
        });
    }

    public void getBookTag(String tag, int start, int limit) {
        if (!isViewAttached()) return;
        mIEBookDetailView.showProgress();
        mEBookDetailMode.getBookByTags(tag, start, limit, new ApiCompleteListener<BooksByTag>() {
            @Override
            public void onSuccess(BooksByTag response) {
                if (!isViewAttached()) return;
                mIEBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast("获取数据失败");
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast(error);
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mIEBookDetailView.hideProgress();
            }
        });
    }

    public void getRecommendBookList(String bookId, int limit) {
        if (!isViewAttached()) return;
        mIEBookDetailView.showProgress();
        mEBookDetailMode.getRecommendBookList(bookId, limit, new ApiCompleteListener<LikedBookList>() {
            @Override
            public void onSuccess(LikedBookList response) {
                if (!isViewAttached()) return;
                mIEBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast("获取数据失败");
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast(error);
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mIEBookDetailView.hideProgress();
            }
        });
    }

    public void getHotReviews(String book, int limit) {
        if (!isViewAttached()) return;
        mIEBookDetailView.showProgress();
        mEBookDetailMode.getHotReview(book, limit, new ApiCompleteListener<HotReview>() {
            @Override
            public void onSuccess(HotReview response) {
                if (!isViewAttached()) return;
                mIEBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast("获取数据失败");
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast(error);
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mIEBookDetailView.hideProgress();
            }
        });
    }

    public void getBookReviewsList(String book, String sort, int start, int limit) {
        if (!isViewAttached()) return;
        mIEBookDetailView.showProgress();
        mEBookDetailMode.getBookReviewsList(book, sort, start, limit, new ApiCompleteListener<HotReview>() {
            @Override
            public void onSuccess(HotReview response) {
                if (!isViewAttached()) return;
                mIEBookDetailView.updateView(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast("获取数据失败");
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mIEBookDetailView.showToast(error);
                mIEBookDetailView.hideProgress();
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mIEBookDetailView.hideProgress();
            }
        });
    }
}
