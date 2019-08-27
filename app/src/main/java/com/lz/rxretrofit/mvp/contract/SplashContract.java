package com.lz.rxretrofit.mvp.contract;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.UpdateBean;

public interface SplashContract {
    interface SplashPresenter{
        void isUpdate();
    }

    interface SplashView {
        void onSuccess(UpdateBean updateBean);
        void onFail(BaseErrorBean baseErrorBean);
    }
}
