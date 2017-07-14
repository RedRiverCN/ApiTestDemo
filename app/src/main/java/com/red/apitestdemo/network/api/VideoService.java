package com.red.apitestdemo.network.api;


import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.bean.VideoListInfo;
import com.red.apitestdemo.bean.dbentity.VideoList;
import com.red.apitestdemo.network.auxiliary.UrlHelper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Chenqk on 2017/6/13. 16:33
 */

public interface VideoService {
    //视频列表
    @GET(UrlHelper.Path.VIDEO_LIST_PATH)
    Observable<VideoListInfo> getVideoListInfo(@Path("id") int id);

    //视频详情
    @GET(UrlHelper.Path.VIDEOINFO_PATH)
    Observable<VideoDetailsInfo> getVideoDetailsInfo(@Path("id") int id);
}
