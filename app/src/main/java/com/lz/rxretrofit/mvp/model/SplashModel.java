package com.lz.rxretrofit.mvp.model;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.UpdateBean;
import com.lz.rxretrofit.httputil.RxScheduler;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.mvp.model.imodel.IsplashModel;
import com.lz.rxretrofit.network.BaseObserver;

public class SplashModel extends BaseModel implements IsplashModel {
    @Override
    public void isUpdate(BaseCallBack<UpdateBean> callBack) {
        getHttpClient()
                .getVersionLocal()
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<UpdateBean>() {
                    @Override
                    public void onSuccess(UpdateBean content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }

                });
    }

}
