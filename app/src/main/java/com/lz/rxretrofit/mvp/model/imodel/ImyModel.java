package com.lz.rxretrofit.mvp.model.imodel;

import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.mvp.model.callback.MyCallBack;

import java.util.List;

public interface ImyModel {
    void findById(String uid,BaseCallBack<TestBean> baseCallBack);
    void findAll(BaseCallBack<List<TestBean>> baseCallBack);
    void deleteById(String uid, MyCallBack baseCallBack);
    void add(TestBean testBean,MyCallBack baseCallBack);
}
