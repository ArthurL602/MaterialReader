package com.ljb.materialreader.ui.presenter;

import com.ljb.materialreader.app.App;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.response.ebook.BooksByTag;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.ui.model.ESearchResultModel;
import com.ljb.materialreader.ui.view.ESearchResultView;
import com.ljb.materialreader.utils.NetWorkUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Author      :meloon
 * Date        :2018/1/12
 * Description :
 */

public class ESearchResultPresent extends BasePresenter<ESearchResultView> {
    private ESearchResultView mESearchResultView;
    private ESearchResultModel mESearchResultModel;

    public ESearchResultPresent(ESearchResultView ESearchResultView, LifecycleProvider provider) {
        mESearchResultView = ESearchResultView;
        mESearchResultModel = new ESearchResultModel();
        mESearchResultModel.setProvider(provider);
    }

    public void searchBooks(String query, final int start, int limit) {
        if (!isViewAttached()) return;
        if (!NetWorkUtils.isConnected(App.getContext())) {
            mESearchResultView.showToast("网络异常");
            mESearchResultView.hideProgress();
            return;
        }
        mESearchResultModel.searchBooks(query, start, limit, new ApiCompleteListener<BooksByTag>() {
            @Override
            public void onSuccess(BooksByTag response) {
                if (!isViewAttached()) return;
                if (start == 0) {
                    mESearchResultView.onRefreshData(response);
                } else {
                    mESearchResultView.onLoadMoreData(response);
                }

            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
                mESearchResultView.showToast("请求失败");
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
                mESearchResultView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
            }
        });

    }

    public void getBooksByTag(String tags, final  int start, int limit) {
        if (!isViewAttached()) return;
        if (!NetWorkUtils.isConnected(App.getContext())) {
            mESearchResultView.showToast("网络异常");
            mESearchResultView.hideProgress();
            return;
        }
        mESearchResultModel.getBooksByTag(tags, start, limit, new ApiCompleteListener<BooksByTag>() {
            @Override
            public void onSuccess(BooksByTag response) {
                if (!isViewAttached()) return;
                if (start == 0) {
                    mESearchResultView.onRefreshData(response);
                } else {
                    mESearchResultView.onLoadMoreData(response);
                }
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
                mESearchResultView.showToast("请求失败");

            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
                mESearchResultView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mESearchResultView.hideProgress();
            }
        });
    }
}
