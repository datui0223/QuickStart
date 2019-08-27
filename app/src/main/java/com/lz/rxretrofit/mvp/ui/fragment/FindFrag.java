package com.lz.rxretrofit.mvp.ui.fragment;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseMvpFragment;
import com.lz.rxretrofit.mvp.presenter.BasePresenter;
import com.lz.rxretrofit.bean.ExchangeMain;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shehuan.statusview.StatusView;
import com.shehuan.statusview.StatusViewBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class FindFrag extends BaseMvpFragment {

    @BindView(R.id.animation_view)
    LottieAnimationView animation_view;
    @BindView(R.id.srl_find)
    SmartRefreshLayout srl_find;
    @BindView(R.id.sv_find)
    StatusView sv;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initParams() {
//        sv = StatusView.init(this,R.id.srl_find);
        animation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srl_find.finishRefresh();
//                sv.showErrorView();
            }
        });

        sv.config(new StatusViewBuilder.Builder()
                .setOnErrorRetryClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sv.showContentView();
                    }
                })
                .build()
        );
        sv.showErrorView();

        srl_find.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(ExchangeMain exchangeMain){
    }

}
