package com.lz.rxretrofit.bean;

public class BaseResponse<T>  {
    private int res_code;
    private String res_msg;
    private T content;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
