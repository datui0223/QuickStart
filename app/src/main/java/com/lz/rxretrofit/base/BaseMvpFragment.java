package com.lz.rxretrofit.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import butterknife.ButterKnife;

public abstract class BaseMvpFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected T mPresenter;
    public abstract int getLayoutId();
    public abstract T getPresenter();
    public abstract void initParams();
    public Context mContext;
    private LoadingPopupView loadingPopupView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = getPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(this);
            initLifecycleObserver(getLifecycle());
        }
        initParams();
    }

    private void initLifecycleObserver(Lifecycle lifecycle) {
        mPresenter.setLifecycleOwner(this);
        lifecycle.addObserver(mPresenter);
    }

    @Override
    public void showProgress() {
        if(loadingPopupView==null){
            loadingPopupView = new XPopup.Builder(mContext).hasShadowBg(true).asLoading();
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
        startActivity(new Intent(mContext,toClass));
    }

    @Override
    public AllApi getHttpClient() {
        return RetrofitUtil.getAllApi();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(mPresenter!=null){
//            mPresenter.detachView();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //统计页面("MainScreen"为页面名称，可自定义)
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    public <T> AutoDisposeConverter<T> autoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
