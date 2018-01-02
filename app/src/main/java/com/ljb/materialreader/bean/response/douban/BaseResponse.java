package com.ljb.materialreader.bean.response.douban;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public class BaseResponse {
    private int code;
    private String  msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseResponse(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public BaseResponse() {

    }
}
