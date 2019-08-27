package com.lz.rxretrofit.mvp.presenter;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.UpdateBean;
import com.lz.rxretrofit.mvp.contract.SplashContract;
import com.lz.rxretrofit.mvp.model.SplashModel;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;

public class SplashPresenter extends BasePresenter<SplashContract.SplashView> implements SplashContract.SplashPresenter{
    SplashModel splashModel;

    public SplashPresenter(){
        splashModel  = new SplashModel();
    }

    @Override
    public void isUpdate() {

        splashModel.isUpdate(new BaseCallBack<UpdateBean>() {
            @Override
            public void onSuccess(UpdateBean response) {
                mView.onSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }
}
