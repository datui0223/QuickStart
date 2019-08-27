package com.lz.rxretrofit.base;

import android.content.Context;
import android.util.Log;

import com.hjq.toast.ToastUtils;
import com.lz.rxretrofit.R;
import com.lz.rxretrofit.httputil.BasicParamsInterceptor;
import com.lz.rxretrofit.httputil.OkhttpUtil;
import com.lz.rxretrofit.utils.SPmanager;
import com.lz.rxretrofit.utils.AppUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;

import androidx.multidex.MultiDexApplication;

import static com.umeng.message.UmengMessageCallbackHandlerService.TAG;

public class BaseApplication extends MultiDexApplication {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        initOkHttpUtil();
        initUmeng();
        initPullRefresh();
        //最强toast初始化
        ToastUtils.init(this);
        //检测内存泄漏
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initPullRefresh() {
        /**
         * 初始化下拉刷新上拉加载控件
         */
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20).setFinishDuration(0);
        });
    }

    public void initUmeng(){
        /**
         * 注意：如果您已经在AndroidManifest.xml中配置过appkey和channel值，可以调用此版本初始化函数。
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"44474a8b28331bdf7f6bde47ec7bacff");
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //友盟分享初始化
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        //获取消息推送代理
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }

    public void initOkHttpUtil(){
        HashMap<String,String> headers = new HashMap<>();
        headers.put("mhmall-notice-token","");
        headers.put("mhmall-platform","");
        headers.put("mhmall-device-name","");
        headers.put("mhmall-seid","");
        headers.put("mhmall-iccid","");
        headers.put("mhmall-meid","");
        headers.put("mhmall-mac","");
        headers.put("mhmall-imei","123123132132");
        headers.put("mhmall-latitude", "");
        headers.put("mhmall-longitude","");
        headers.put("mhmall-uuid","");
        headers.put("Accept-Encoding", "gzip");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        HashMap<String,String> params = new HashMap<>();
        params.put("version", AppUtil.getVersion());
        params.put("terminal", "Android");
        params.put("client", "APP");
        params.put("token", SPmanager.getToken());
        /**
         * 设置共同参数
         */
        BasicParamsInterceptor paramsInterceptor = new BasicParamsInterceptor
                .Builder()
//                .addParamsMap(params)//添加公共参数到post
                .addQueryParamsMap(params)//添加公共参数到url
                .addHeaderParamsMap(headers)//添加公共头
                .build();
        OkhttpUtil.getInstance().setInterceptor(paramsInterceptor);
    }
}
