package com.lz.rxretrofit.mvp.model.callback;

import com.lz.rxretrofit.bean.BaseErrorBean;

public interface BaseCallBack<T> {
    void onSuccess(T response);
    void onFail(BaseErrorBean baseErrorBean);
}
