package com.lz.rxretrofit.network;

import com.lz.rxretrofit.bean.HomeProListBean;
import com.lz.rxretrofit.bean.LoginBean;
import com.lz.rxretrofit.bean.MiHomeBean;
import com.lz.rxretrofit.bean.TestBean;
import com.lz.rxretrofit.bean.UpdateBean;
import com.lz.rxretrofit.bean.UserBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface AllApi {
    /**
     * 返回类型为ResponseBody类型能拿到所有数据
     * Observable可以为Call
     * 参数设置为Body才能设置contentType
     * @param userBean
     * @return
     */
    String BASE_URL = "https://uat-wap.mihangmall.com";
    String TESTNURL = "https://xiaoshu.moshield.cn";
    String testUrlName = "urlname:testUrl";
    String TEST2NURL = "http://192.168.2.66:8080";
    String test2UrlName = "urlname:test2Url";


    @POST("/auth/tokens")
    Observable<LoginBean> doLogin(@Body UserBean userBean);

    @POST("/auth/{tokens}")
    Observable<LoginBean> doLogin(@Body Map<String,String> map, @Path("tokens") String tokens);

    @GET("/mall/home")
    Observable<MiHomeBean> getHomeData();

    @PUT("/loan/credits")
    Observable<ResponseBody> postSth(@Body Map<String,String> tokenKey);

    @GET("/buyer/goods/search/tags-goods")
    Observable<HomeProListBean> getGoods(@Query("page_no") int page_no,@Query("page_size") int page_size,@Query("tags") String tag);

    @Headers(testUrlName)
    @GET("/loan/update")
    Observable<UpdateBean> getVersion();

    @Headers(test2UrlName)
    @GET("/updateApk")
    Observable<UpdateBean> getVersionLocal();

    @Headers(test2UrlName)
    @GET("/findAll")
    Observable<List<TestBean>> findAll();

    @Headers(test2UrlName)
    @GET("/findOne/{uid}")
    Observable<TestBean> findById(@Path("uid") String uid);

    @Headers(test2UrlName)
    @DELETE("/deleteUser")
    Observable<Boolean> deleteById(@Query("uid") String uid);

    @Headers(test2UrlName)
    @POST("/addUser")
    Observable<Boolean> addOne(@Body TestBean testBean);

    @Headers(test2UrlName)
    @PUT("/updateUser")
    Observable<Boolean> updateOne(@Body TestBean testBean);

    @GET()
    @Streaming
    Flowable<ResponseBody> download(@Url String url);












}
