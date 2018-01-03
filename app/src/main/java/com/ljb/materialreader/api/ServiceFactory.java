package com.ljb.materialreader.api;

import android.util.Log;

import com.ihsanbal.logging.LoggingInterceptor;
import com.ljb.materialreader.app.App;
import com.ljb.materialreader.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author      :ljb
 * Date        :2018/1/2
 * Description :
 */

public class ServiceFactory {
    private static OkHttpClient mOkHttpClient;
    private static Retrofit sRetrofit;
    //默认缓存大小（20M）
    public static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 20;

    public static final int DEFAULT_MAX_AGE = 60 * 60;
    public static final int DEFAULT_MAX_STALE_ONLINE = DEFAULT_MAX_AGE * 24;
    public static final int DEFAULT_MAX_STALE_OFFLINE = DEFAULT_MAX_STALE_ONLINE * 7;
    public static OkHttpClient sOkHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (sOkHttpClient == null) {
                    File cacheFile = new File(App.getContext().getCacheDir(), "response");
                    Cache cache = new Cache(cacheFile, DEFAULT_CACHE_SIZE);
                    LoggingInterceptor.Builder builder = new LoggingInterceptor.Builder();
                    sOkHttpClient = new OkHttpClient.Builder()//
                            .cache(cache)//设置缓存策略
                            .addInterceptor(RESPONSE_INTERCEPTOR)//设置缓存策略
                            .addNetworkInterceptor(RESPONSE_INTERCEPTOR)//设置缓存策略
                            .addInterceptor(builder.build())//设置打印loginterceptor
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    public static Retrofit getmRetrofit(String baseUrl) {
        if (sRetrofit == null) {
            synchronized (Retrofit.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()//
                            .client(getOkHttpClient())//
                            .addConverterFactory(GsonConverterFactory.create())//
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                            .baseUrl(baseUrl).build();

                }
            }

        }
        return sRetrofit;
    }

    public static <T> T createService(String baseUrl, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()//
                .baseUrl(baseUrl)//
                .client(getOkHttpClient())//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                .addConverterFactory(GsonConverterFactory.create())//
                .build();
        return retrofit.create(clz);
    }

    private static final Interceptor RESPONSE_INTERCEPTOR = new Interceptor() {
        //针对那些服务器不支持缓存策略的情况下，使用强制修改响应头，达到缓存的效果
        //响应拦截只不过是出于规范，向服务器发出请求，至于服务器搭不搭理我们我们不管他，
        // 我们在响应里面做手脚，有网没有情况下的缓存策略
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

//            CacheControl cacheControl = new CacheControl.Builder()//
//                    .maxAge(1, TimeUnit.MINUTES)//
//                    .build();
//            request = request.newBuilder()//
//                    .cacheControl(cacheControl)//
//                    .build();
            if (NetWorkUtils.isConnected(App.getContext())) {//有网络
                return chain.proceed(request).newBuilder()//
                        .header("Cache-Control", "public ,max-age=" + DEFAULT_MAX_STALE_ONLINE)//
                        .removeHeader("Pragma")//
                        .build();
            } else {//没有网络
                Log.e("TAG", "没有网络");
                return chain.proceed(request).newBuilder()//
                        .header("Cache-Control", "public ,only-if-cache,max-stale=" + DEFAULT_MAX_STALE_OFFLINE)//
                        .removeHeader("Pragma")//
                        .build();
            }
        }
    };
}
