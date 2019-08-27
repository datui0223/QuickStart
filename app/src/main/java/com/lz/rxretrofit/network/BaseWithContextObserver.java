package com.lz.rxretrofit.network;

import android.content.Context;

import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lz.rxretrofit.bean.BaseErrorBean;

public abstract class BaseWithContextObserver<T> extends BaseObserver<T> {
    private LoadingPopupView loadingPopupView;
    private Context context;
    public BaseWithContextObserver(Context context) {
        this.context = context;
        if(loadingPopupView==null){
            loadingPopupView = new XPopup.Builder(context).asLoading();
        }
        if(!loadingPopupView.isShow()){
            loadingPopupView.show();
        }
    }
    public BaseWithContextObserver(Context context,Boolean isShowLoading) {
        if(isShowLoading){
            if(loadingPopupView==null){
                loadingPopupView = new XPopup.Builder(context).asLoading();
            }
            if(!loadingPopupView.isShow()){
                loadingPopupView.show();
            }
        }
    }

    @Override
    public void onSuccess(T content) {
        if(loadingPopupView!=null){
            loadingPopupView.dismiss();
        }
        if(content!=null){
            onSuccessWithDialog(content);
        }else {
            ToastUtils.show("成功解析异常");
        }
    }

    @Override
    public void onFailure(BaseErrorBean baseErrorBean) {
        if(loadingPopupView!=null){
            loadingPopupView.dismiss();
        }
        onFailureWithDialog(baseErrorBean);
    }

    public abstract void onSuccessWithDialog(T content);
    public abstract void onFailureWithDialog(BaseErrorBean baseErrorBean);
}
