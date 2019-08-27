package com.lz.rxretrofit.mvp.model;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.httputil.RxScheduler;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.mvp.model.callback.MyCallBack;
import com.lz.rxretrofit.mvp.model.imodel.ImyModel;
import com.lz.rxretrofit.network.BaseObserver;

import java.util.List;

public class MyModel extends BaseModel implements ImyModel {
    @Override
    public void findById(String uid,BaseCallBack<TestBean> baseCallBack) {
        getHttpClient().findById(uid)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<TestBean>() {
                    @Override
                    public void onSuccess(TestBean content) {
                        baseCallBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        baseCallBack.onFail(baseErrorBean);
                    }
                });
    }

    @Override
    public void findAll(BaseCallBack<List<TestBean>> baseCallBack) {
        getHttpClient()
                .findAll()
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<List<TestBean>>() {
                    @Override
                    public void onSuccess(List<TestBean> content) {
                        baseCallBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        baseCallBack.onFail(baseErrorBean);
                    }
                });

    }

    @Override
    public void deleteById(String uid, MyCallBack baseCallBack) {
        getHttpClient().deleteById(uid)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean content) {
                        baseCallBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        baseCallBack.onFail(baseErrorBean);
                    }
                });
    }

    @Override
    public void add(TestBean testBean,MyCallBack baseCallBack) {
        getHttpClient().addOne(testBean)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean content) {
                        baseCallBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        baseCallBack.onFail(baseErrorBean);
                    }
                });
    }
}
