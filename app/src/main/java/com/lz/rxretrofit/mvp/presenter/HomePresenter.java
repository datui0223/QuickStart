package com.lz.rxretrofit.mvp.presenter;

import android.annotation.SuppressLint;

import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MiHomeBean;
import com.lz.rxretrofit.bean.UserBean;
import com.lz.rxretrofit.httputil.RxScheduler;
import com.lz.rxretrofit.mvp.contract.HomeContract;
import com.lz.rxretrofit.mvp.model.HomeModel;
import com.lz.rxretrofit.mvp.model.callback.BaseCallBack;
import com.lz.rxretrofit.network.BaseObserver;
import com.lz.rxretrofit.utils.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import okhttp3.ResponseBody;

public class HomePresenter extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter{
    private HomeModel homeModel;

    //此参数可以连接fragment生命周期解决内存泄漏
    private boolean isStop;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public HomePresenter(){
        homeModel = new HomeModel();
    }
    public void getGoods(int page_no) {
        homeModel.getGoods(page_no, new BaseCallBack<HomeProListBean>() {
            @Override
            public void onSuccess(HomeProListBean response) {
                mView.onSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onFail(baseErrorBean);
            }
        });
    }

    public void getData() {
        homeModel.getData(new BaseCallBack<MiHomeBean>() {
            @Override
            public void onSuccess(MiHomeBean response) {
                mView.onDataSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onDataFail(baseErrorBean);
            }
        });
    }
    @SuppressLint("CheckResult")
    public void doLogin() {
        Observable.interval(0,2, TimeUnit.SECONDS)
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        if(isStop){
                            return false;
                        }else {
                            return true;
                        }
                    }
                })
                .compose(new RxScheduler<>())
                .as(bindLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if(isViewAttached()){
                            UserBean userBean = new UserBean();
                            userBean.setMobile("13227822182");
                            userBean.setPasswd(SecurityUtils.MD5("a000000"));
                            homeModel.doLogin(userBean)
                                    .subscribe(new BaseObserver<LoginBean>() {
                                        @Override
                                        public void onSuccess(LoginBean content) {
                                            mView.onLoginSuccess(content);
                                        }

                                        @Override
                                        public void onFailure(BaseErrorBean baseErrorBean) {
                                            mView.onCommonFail(baseErrorBean);
                                        }
                                    });

                        }
                    }
                });
    }

    public void putSth() {
        Map<String,String> map = new HashMap<>();
        map.put("tokenKey","13227822182");
        homeModel.putSth(map, new BaseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                mView.onSthSuccess(response);
            }

            @Override
            public void onFail(BaseErrorBean baseErrorBean) {
                mView.onCommonFail(baseErrorBean);
            }
        });
    }

}
