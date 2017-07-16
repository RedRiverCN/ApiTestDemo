package com.red.apitestdemo.network;

import android.util.Log;

import com.red.apitestdemo.base.request.BaseRetrofit;
import com.red.apitestdemo.network.api.VideoService;
import com.red.apitestdemo.network.auxiliary.ApiConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class RetrofitHelper extends BaseRetrofit {

    private volatile static RetrofitHelper instance;

    //获取单例
    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        super();
    }

    @Override
    public OkHttpClient initOkHttp() {
        //定制OkHttp
        //配置超时，缓存，日志等
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);

        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("network","OkHttp====Message:"+message));
        loggingInterceptor.setLevel(level);
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);

        return httpClientBuilder.build();
    }

    /**
     * 获取各种api服务
     */
    public VideoService getVideoAPI(){
        return getApiService(VideoService.class, ApiConstants.VIDEO_BASE_URL);
    }



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
}
