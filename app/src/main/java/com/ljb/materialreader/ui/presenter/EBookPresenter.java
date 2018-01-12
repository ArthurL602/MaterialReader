package com.ljb.materialreader.ui.presenter;

import com.ljb.materialreader.app.App;
import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.response.ebook.CategoryList;
import com.ljb.materialreader.bean.response.ebook.HotWords;
import com.ljb.materialreader.bean.response.ebook.Rankings;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.ui.model.EBookListModel;
import com.ljb.materialreader.ui.view.EBookListView;
import com.ljb.materialreader.utils.NetWorkUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Author      :meloon
 * Date        :2018/1/11
 * Description :
 */

public class EBookPresenter extends BasePresenter<EBookListView> {

    private EBookListView mEBookListView;
    private EBookListModel mEBookListModel;

    public EBookPresenter(EBookListView EBookListView, LifecycleProvider provider) {
        mEBookListView = EBookListView;
        mEBookListModel = new EBookListModel();
        mEBookListModel.setProvider(provider);
    }


    public void getRating(String rankingId) {
        if (!isViewAttached()) return;
        if (!NetWorkUtils.isConnected(App.getContext())) {
            mEBookListView.showToast("网络连接失败，请检查网络");
            return;
        }
        mEBookListView.showProgress();
        mEBookListModel.getRating(rankingId, new ApiCompleteListener<Rankings>() {
            @Override
            public void onSuccess(Rankings response) {
                if (!isViewAttached()) return;
                mEBookListView.onRefresh(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast("请求失败，请重新加载");
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
            }
        });
    }

    /**
     * 获取分类列表
     */
    public void getCategoryList() {
        if (!isViewAttached()) return;
        if (!NetWorkUtils.isConnected(App.getContext())) {
            mEBookListView.showToast("网络异常！！");
            return;
        }
        mEBookListView.showProgress();
        mEBookListModel.getCategoryList(new ApiCompleteListener<CategoryList>() {
            @Override
            public void onSuccess(CategoryList response) {
                if (!isViewAttached()) return;
                mEBookListView.onRefresh(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast("请求失败");
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
            }
        });
    }

    public void getHotWords() {
        if (!isViewAttached()) return;
        if (!NetWorkUtils.isConnected(App.getContext())) {
            mEBookListView.showToast("网络异常");
            mEBookListView.hideProgress();
            return;
        }
        mEBookListModel.getHotWords(new ApiCompleteListener<HotWords>() {
            @Override
            public void onSuccess(HotWords response) {
                if (!isViewAttached()) return;
                mEBookListView.onRefresh(response);
            }

            @Override
            public void onFailure() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast("请求失败");
            }

            @Override
            public void onError(String error) {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
                mEBookListView.showToast(error);
            }

            @Override
            public void onComplete() {
                if (!isViewAttached()) return;
                mEBookListView.hideProgress();
            }
        });

    }
}
