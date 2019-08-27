package com.lz.rxretrofit.httputil;

import com.lz.rxretrofit.network.AllApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static AllApi allApi;
    private Retrofit retrofit;
    private static volatile RetrofitUtil retrofitUtil;


    public static AllApi getAllApi(){
        if(allApi==null){
            allApi = getInstance().getRetrofit().create(AllApi.class);
        }
        return allApi;
    }

    /**
     * 获取Retrofit
     * @return
     */
    public Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .client(OkhttpUtil.getInstance().getOkHttp())
                    .baseUrl(AllApi.BASE_URL)
                    //设置解析转换工厂
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    /**
     * 单例创建RetrofitUtil
     * @return
     */
    public static RetrofitUtil getInstance(){
        if(retrofitUtil==null){
            synchronized (RetrofitUtil.class){
                if(retrofitUtil==null){
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

}
