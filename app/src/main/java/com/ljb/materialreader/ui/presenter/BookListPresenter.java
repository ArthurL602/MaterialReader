package com.ljb.materialreader.ui.presenter;

import com.ljb.materialreader.base.BasePresenter;
import com.ljb.materialreader.bean.response.douban.BookListResponse;
import com.ljb.materialreader.callback.ApiCompleteListener;
import com.ljb.materialreader.ui.model.BookListModel;
import com.ljb.materialreader.ui.view.BookListView;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public class BookListPresenter extends BasePresenter {
    private BookListView mBookListView;
    private BookListModel mBookListModel;

    public BookListPresenter(BookListView bookListView) {
        mBookListView = bookListView;
        mBookListModel = new BookListModel();
    }

    /**
     * 加载数据
     */
    public void loadBooks(String q, String tag, int start, int count, final String fields) {
        if (!isViewAttached()) {
            return;
        }
        mBookListView.showProgress();
        mBookListModel.loadBookList(q, tag, start, count, fields, new ApiCompleteListener<BookListResponse>() {
            @Override
            public void onSuccess(BookListResponse response) {
                if (isViewAttached()) {
                    int start = response.getStart();
                    if (start == 0) {
                        mBookListView.refreshData(response);

                    } else {
                        mBookListView.addData(response);
                    }
                }
            }

            @Override
            public void onFailure() {
                if (isViewAttached()) {
                    mBookListView.hideProgress();
                    mBookListView.showToast("请求失败，请重新获取");
                }
            }

            @Override
            public void onError(String error) {
                if (isViewAttached()) {
                    mBookListView.hideProgress();
                    mBookListView.showToast(error);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    mBookListView.hideProgress();
                }
            }
        });
    }
}
