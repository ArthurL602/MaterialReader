package com.ljb.materialreader.ui.view;

import com.ljb.materialreader.base.BaseView;
import com.ljb.materialreader.bean.response.douban.BookListResponse;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public interface BookListView extends BaseView {
    void refreshData(BookListResponse result);

    void addData(BookListResponse result);
}
