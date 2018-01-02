package com.ljb.materialreader.callback;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :网络请求返回接口
 */

public interface ApiCompleteListener<T> {
    void onSuccess(T response);

    void onFailure();

    void onError(String error);

    void onComplete();
}
