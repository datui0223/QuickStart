package com.lz.rxretrofit.mvp.model;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MiHomeBean;
import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.bean.UserBean;
import com.lz.rxretrofit.httputil.RxScheduler;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.mvp.model.imodel.IhomeModel;
import com.lz.rxretrofit.network.BaseObserver;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
public class HomeModel extends BaseModel implements IhomeModel {


    @Override
    public Observable<LoginBean> doLogin(UserBean userBean) {
        return getHttpClient().doLogin(userBean).compose(new RxScheduler<>());
    }

    @Override
    public void getGoods(int page_no, BaseCallBack<HomeProListBean> callBack) {
        getHttpClient()
                .getGoods(page_no,6,"recommend")
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<HomeProListBean>() {
                    @Override
                    public void onSuccess(HomeProListBean content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }

                });
    }

    @Override
    public void getData(BaseCallBack<MiHomeBean> callBack) {
        getHttpClient()
                .getHomeData()
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<MiHomeBean>() {

                    @Override
                    public void onSuccess(MiHomeBean content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }

                });
    }

    @Override
    public void putSth(Map<String, String> map, BaseCallBack<ResponseBody> callBack) {
        getHttpClient()
                .postSth(map)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<ResponseBody>() {

                    @Override
                    public void onSuccess(ResponseBody content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }

                });
    }

    @Override
    public void findAll(BaseCallBack<List<TestBean>> callBack) {
        getHttpClient()
                .findAll()
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<List<TestBean>>() {

                    @Override
                    public void onSuccess(List<TestBean> content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }
                });
    }

    @Override
    public void addOne(TestBean testBean,BaseCallBack callBack) {
        getHttpClient()
                .addOne(testBean)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }
                });
    }

    @Override
    public void updateOne(TestBean testBean, BaseCallBack callBack) {
        getHttpClient()
                .updateOne(testBean)
                .compose(new RxScheduler<>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean content) {
                        callBack.onSuccess(content);
                    }

                    @Override
                    public void onFailure(BaseErrorBean baseErrorBean) {
                        callBack.onFail(baseErrorBean);
                    }
                });

    }

}
