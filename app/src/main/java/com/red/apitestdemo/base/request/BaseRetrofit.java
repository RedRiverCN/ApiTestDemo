package com.red.apitestdemo.base.request;

import com.google.gson.GsonBuilder;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：祝文飞（Tailyou）
 * 邮箱：tailyou@163.com
 * 时间：2017/2/15 14:06
 * 描述：Retrofit基类
 */

public abstract class BaseRetrofit {

    private static OkHttpClient sOkHttpClient = null;

    public abstract OkHttpClient initOkHttp();

    public BaseRetrofit() {
        sOkHttpClient = initOkHttp();
    }

    protected <T> T getApiService(Class<T> clz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(clz);
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<HttpResponse<T>, T> handleResult() {
        return httpResponseObservable -> httpResponseObservable.map(httpResponse -> {
            if (httpResponse.getStatus().equals(HttpResponse.HTTP_STATUS_SUCCESS)) {
                return httpResponse.getData();
            } else {
                throw new HttpException(httpResponse.getMsg());
            }
        });
    }

}
