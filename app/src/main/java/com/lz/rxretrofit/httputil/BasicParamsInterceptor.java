package com.lz.rxretrofit.httputil;

import android.text.TextUtils;

import com.lz.rxretrofit.network.AllApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class BasicParamsInterceptor implements Interceptor {
    Map<String, String> queryParamsMap = new HashMap<>(); // 添加到 URL 末尾
    Map<String, String> paramsMap = new HashMap<>(); // 添加到公共参数到消息体，适用 Post 请求
    Map<String, String> headerParamsMap = new HashMap<>(); // 公共 Headers 添加

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        //获取老的url
        HttpUrl httpUrl = request.url();
        //获取头信息集合
        List<String> urlList = request.headers("urlname");
        //是否有新的baseurl
        boolean isNewBaseUrl = false;
        HttpUrl newHttpUrl = null;
        if(urlList!=null&&urlList.size()>0){
            //清除headers
            requestBuilder.removeHeader("urlname");
            HttpUrl baseUrl = null;
            if("testUrl".equals(urlList.get(0))){
                baseUrl = HttpUrl.parse(AllApi.TESTNURL);
            }
            else if("test2Url".equals(urlList.get(0))){
                baseUrl = HttpUrl.parse(AllApi.TEST2NURL);
            }
            //重新创建HttpUrl
            newHttpUrl = httpUrl.newBuilder()
                    .scheme(baseUrl.scheme())
                    .host(baseUrl.host())
                    .port(baseUrl.port())
                    .build();
            isNewBaseUrl = true;
        }
        // 添加公共请求头
        if (headerParamsMap.size() > 0) {
            requestBuilder.headers(Headers.of(headerParamsMap));
        }
        if(isNewBaseUrl){
            if (queryParamsMap.size() > 0) {
                requestBuilder = injectParamsIntoUrl(newHttpUrl.newBuilder(), requestBuilder, queryParamsMap);
            }
        }else {
            // 添加get请求公共参数
            if (queryParamsMap.size() > 0) {
                requestBuilder = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, queryParamsMap);
//            if (!canInjectIntoBody(request)) {
//                requestBuilder = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, queryParamsMap);
//            }
            }
        }

        // 添加post请求公共参数
        if (paramsMap.size() > 0) {
            if (canInjectIntoBody(request)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    formBodyBuilder.add(entry.getKey(), entry.getValue());
                }
                RequestBody formBody = formBodyBuilder.build();
                String postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
                requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            }
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     *  确认是否是 post 请求
     * @param request 发出的请求
     * @return true 需要注入公共参数
     */
    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
//        MediaType mediaType = body.contentType();
//        if (mediaType == null) {
//            return false;
//        }
//        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
//            return false;
//        }
        return true;
    }

    private Request.Builder injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder;
        }
        return null;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    public static class Builder {

        BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        // 添加公共参数到 post 消息体
        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }

        // 添加公共参数到 post 消息体
        public Builder addParamsMap(Map<String, String> paramsMap) {
            if(paramsMap!=null){
                interceptor.paramsMap.putAll(paramsMap);
            }
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            if(headerParamsMap!=null){
                interceptor.headerParamsMap.putAll(headerParamsMap);
            }
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            if(queryParamsMap!=null){
                interceptor.queryParamsMap.putAll(queryParamsMap);
            }
            return this;
        }

        public BasicParamsInterceptor build() {
            return interceptor;
        }

    }
}