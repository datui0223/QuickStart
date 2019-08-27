package com.lz.rxretrofit.base;


import com.lz.rxretrofit.network.AllApi;

public interface BaseView {
    /**
     * 显示加载动画
     */
    void showProgress();

    /**
     * 关闭加载动画
     */
    void hideProgress();

    /**
     * 跳转页面
     */
    void startActivitySample(Class toClass);

    /**
     * 獲取http
     */
    AllApi getHttpClient();

}
