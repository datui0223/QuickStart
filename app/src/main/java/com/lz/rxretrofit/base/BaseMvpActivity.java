package com.lz.rxretrofit.base;

import android.content.Intent;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.httputil.RetrofitUtil;
import com.lz.rxretrofit.mvp.presenter.BasePresenter;
import com.lz.rxretrofit.network.AllApi;
import com.shehuan.statusview.StatusView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import butterknife.ButterKnife;

public abstract class BaseMvpActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    private LoadingPopupView loadingPopupView;
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = getPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(this);
            initLifecycleObserver(getLifecycle());
        }
        //初始化沉浸式
        ImmersionBar.with(this).init();
        //绑定控件
        ButterKnife.bind(this);
        initParams();
        //友盟统计
        PushAgent.getInstance(this).onAppStart();
    }

    private void initLifecycleObserver(Lifecycle lifecycle) {
        mPresenter.setLifecycleOwner(this);
        lifecycle.addObserver(mPresenter);
    }

    public abstract int getLayoutId();
    public abstract void initParams();
    public abstract T getPresenter();

    @Override
    public void showProgress() {
        if(loadingPopupView==null){
            loadingPopupView = new XPopup.Builder(this).asLoading();
            loadingPopupView.show();
        }else {
            if(!loadingPopupView.isShow()){
                loadingPopupView.show();
            }
        }
    }


    @Override
    public void hideProgress() {
        if(loadingPopupView!=null){
            loadingPopupView.dismiss();
        }
    }


    @Override
    public void startActivitySample(Class toClass) {
        startActivity(new Intent(this, toClass));
    }


    @Override
    public AllApi getHttpClient() {
        return RetrofitUtil.getAllApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 在BasePresenter中使用LifecycleObserver监听生命周期
         * 故此不需要
         */
//        if(mPresenter!=null){
//            mPresenter.detachView();
//        }
    }

    public <T> AutoDisposeConverter<T> autoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        //当用户使用自有账号登录时，可以这样统计：
//        MobclickAgent.onProfileSignIn("userID");
//        //当用户使用第三方账号（如新浪微博）登录时，可以这样统计：
//        MobclickAgent.onProfileSignIn("WB","userID");
//        //登出
//        MobclickAgent.onProfileSignOff();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
