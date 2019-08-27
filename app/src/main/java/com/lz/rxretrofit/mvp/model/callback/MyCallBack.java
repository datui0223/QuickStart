package com.lz.rxretrofit.mvp.model.callback;

import com.lz.rxretrofit.bean.BaseErrorBean;

public interface MyCallBack {
    void onSuccess(boolean b);
    void onFail(BaseErrorBean baseErrorBean);
}
