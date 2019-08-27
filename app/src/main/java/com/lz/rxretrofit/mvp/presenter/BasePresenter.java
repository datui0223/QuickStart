package com.lz.rxretrofit.mvp.presenter;

import com.lz.rxretrofit.httputil.RetrofitUtil;
import com.lz.rxretrofit.network.AllApi;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class BasePresenter<V> implements Ipresenter{
    protected V mView;
    protected LifecycleOwner owner;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view ) {
        this.mView = view;
    }

    /**
     * 如使用LifecycleObserver监听生命周期此方法作废
     * 解除绑定view，一般在onDestroy中调用
     */

//    public void detachView() {
//        this.mView = null;
//    }

    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }


    public AllApi getHttpClient() {
        return RetrofitUtil.getAllApi();
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == owner)
            throw new NullPointerException("lifecycleOwner == null");
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_PAUSE));
    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.owner = lifecycleOwner;
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
//        this.mView = v;
    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        this.mView = null;
        this.owner = null;
    }

    @Override
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {

    }
}
