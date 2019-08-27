package com.lz.rxretrofit.mvp.contract;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MiHomeBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface HomeContract {
    interface HomePresenter {
        void getGoods(int page_no);
        void getData();
        void doLogin();
        void putSth();
    }
    interface HomeView {
        void onSuccess(HomeProListBean content);
        void onFail(BaseErrorBean baseErrorBean);
        void onDataSuccess(MiHomeBean content);
        void onDataFail(BaseErrorBean baseErrorBean);
        void onLoginSuccess(LoginBean content);
        void onSthSuccess(ResponseBody content);
        void onCommonFail(BaseErrorBean baseErrorBean);

    }
    interface HomeModel{
        Observable<MiHomeBean> getData();
    }
}