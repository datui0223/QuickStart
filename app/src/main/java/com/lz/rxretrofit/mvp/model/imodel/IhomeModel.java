package com.lz.rxretrofit.mvp.model.imodel;

import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MiHomeBean;
import com.lz.rxretrofit.bean.UserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IhomeModel {
    Observable<LoginBean> doLogin(UserBean userBean);
    void getGoods(int page_no,BaseCallBack<HomeProListBean> callBack);
    void getData(BaseCallBack<MiHomeBean> callBack);
    void putSth(Map<String,String> map,BaseCallBack<ResponseBody> callBack);
    void findAll(BaseCallBack<List<TestBean>> callBack);
    void addOne(TestBean testBean,BaseCallBack callBack);
    void updateOne(TestBean testBean,BaseCallBack callBack);
}
