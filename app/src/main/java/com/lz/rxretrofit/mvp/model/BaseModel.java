package com.lz.rxretrofit.mvp.model;

import com.lz.rxretrofit.httputil.RetrofitUtil;
import com.lz.rxretrofit.mvp.presenter.Ipresenter;
import com.lz.rxretrofit.network.AllApi;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class BaseModel{
    public AllApi getHttpClient(){
        return RetrofitUtil.getAllApi();
    }

}
