package com.lz.rxretrofit.httputil;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpUtil {
    public static volatile OkhttpUtil okhttpUtil;
    private OkHttpClient okHttpClient;
    private Interceptor mInterceptor;
    /**
     * 单例创建okhttpclient
     * @return
     */
    public static OkhttpUtil getInstance(){
        if(okhttpUtil==null){
            synchronized (OkhttpUtil.class){
                if(okhttpUtil==null){
                    okhttpUtil = new OkhttpUtil();
                }
            }
        }
        return okhttpUtil;
    }

    public Interceptor getInterceptor() {
        return mInterceptor;
    }

    public void setInterceptor(Interceptor mInterceptor) {
        this.mInterceptor = mInterceptor;
    }

    public OkHttpClient getOkHttp(){
        if(okHttpClient==null){
            okHttpClient = getOkBuilder().build();
        }
        return okHttpClient;
    }
    /**
     * 创建OkhttpBuilder
     *
     */
    private OkHttpClient.Builder getOkBuilder(){
        /**
         * 创建日志
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("SIMPLEokhttp",message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                ;
        return okBuilder;
    }

}
