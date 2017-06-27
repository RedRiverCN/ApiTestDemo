package com.red.apitestdemo.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.red.apitestdemo.network.api.VideoService;
import com.red.apitestdemo.network.auxiliary.ApiConstants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static GsonConverterFactory sFactory = GsonConverterFactory.create(new GsonBuilder().create());
/*
    设置OkHttpClient
    static {
        initOkHttpClient();
    }

    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (sOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (sOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    sOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }
*/

    private static OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(sFactory)
                .build();

        return retrofit.create(clazz);
    }


    public static VideoService getVideoAPI(){
        return createApi(VideoService.class, ApiConstants.VIDEO_BASE_URL);
    }

    //添加其他的api
    public static VideoService getOtherAPI(){
        return createApi(VideoService.class, ApiConstants.VIDEO_BASE_URL);
    }

}
