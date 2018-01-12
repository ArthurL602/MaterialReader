package com.ljb.materialreader.ui.view;

import com.ljb.materialreader.base.BaseView;

/**
 * Author      :meloon
 * Date        :2018/1/12
 * Description :
 */

public interface ESearchResultView extends BaseView {
    void onRefreshData(Object data);

    void onLoadMoreData(Object data);

}
