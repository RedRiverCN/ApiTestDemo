package com.red.apitestdemo.network.api;


import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.network.auxiliary.UrlHelper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Chenqk on 2017/6/13. 16:33
 */

public interface VideoService {

    @GET(UrlHelper.Path.VIDEO_PATH_ID)
    Observable<VideoBean> getVideo(@Path("id") int id);
}
