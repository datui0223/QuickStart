package com.lz.rxretrofit.network;

import com.google.gson.Gson;
import com.lz.rxretrofit.bean.BaseErrorBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof HttpException){
            ResponseBody body = ((HttpException)e).response().errorBody();
            Gson gson = new Gson();
            try {
                BaseErrorBean res = gson.fromJson(body.string(), BaseErrorBean.class);
                onFailure(res);
            } catch (Exception e1) {
//                ToastUtils.show("解析异常");
                onFailure(new BaseErrorBean("解析异常",""));
            }
        }
        else {
            onFailure(new BaseErrorBean("网络异常",""));
//            BaseApplication.appContext.startActivity(new Intent(BaseApplication.appContext, GuideActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            ToastUtils.show("网络异常");
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T content);
    public abstract void onFailure(BaseErrorBean baseErrorBean);
}
