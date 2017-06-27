package com.red.apitestdemo.network.api;

import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.network.auxiliary.UrlHelper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Red on 2017/6/24.
 */

public interface OtherServer {
    @GET("demo/video/find/{id}")
    Observable<VideoBean> getVideo(@Path("id") int id);
}