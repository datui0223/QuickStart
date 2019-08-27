package com.lz.rxretrofit.mvp.model.imodel;

import com.lz.rxretrofit.bean.UpdateBean;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;

public interface IsplashModel {
    void isUpdate(BaseCallBack<UpdateBean> callBack);
}
