package com.lz.rxretrofit.mvp.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.hjq.toast.ToastUtils;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.base.BaseMvpFragment;
import com.lz.rxretrofit.bean.BaseErrorBean;
import com.lz.rxretrofit.bean.ExchangeMain;
import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.mvp.contract.MyContract;
import com.lz.rxretrofit.mvp.presenter.MyPresenter;
import com.shehuan.statusview.StatusView;
import com.shehuan.statusview.StatusViewBuilder;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class MyFrag extends BaseMvpFragment<MyPresenter> implements MyContract.MyView {
    @BindView(R.id.sv_my)
    StatusView sv;
    @BindView(R.id.bt_findall)
    Button bt_findall;
    @BindView(R.id.bt_findbyid)
    Button bt_findbyid;
    @BindView(R.id.bt_add)
    Button bt_add;
    @BindView(R.id.bt_deletebyid)
    Button bt_deletebyid;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public MyPresenter getPresenter()
    {
        return new MyPresenter();
    }

    @Override
    public void initParams() {
        //设置切换布局
        sv.config(new StatusViewBuilder.Builder()
                .setOnErrorRetryClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sv.showContentView();
                    }
                })
                .build()
        );

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBean testBean = new TestBean();
                testBean.setId(1);
                testBean.setName("lizhen");
                testBean.setAge(29);
                mPresenter.add(testBean);
            }
        });
        bt_findall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.findAll();
            }
        });
        bt_findbyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.findById("23");
            }
        });
        bt_deletebyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteById("23");
            }
        });
    }

    private void shareAll() {
        new ShareAction(getActivity()).withText("hello").setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.MORE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).open();
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

    @Override
    public void onFindAllSuccess(List<TestBean> lstb) {
        ToastUtils.show(lstb.toString());
    }

    @Override
    public void onFindByIdSuccess(TestBean testBean) {
        ToastUtils.show(testBean.toString());
    }

    @Override
    public void onDeleteByIdSuccess(boolean b) {
        ToastUtils.show(b);
    }

    @Override
    public void onAddSuccess(boolean b) {
        ToastUtils.show(b);
    }

    @Override
    public void onFail(BaseErrorBean baseErrorBean) {
        ToastUtils.show(baseErrorBean.getMessage());
    }
}
