package com.red.apitestdemo.network.api;


import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.bean.VideoListInfo;
import com.red.apitestdemo.bean.dbentity.VideoList;
import com.red.apitestdemo.network.auxiliary.UrlHelper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Chenqk on 2017/6/13. 16:33
 */

public interface VideoService {
    //视频列表
    @GET(UrlHelper.Path.VIDEO_LIST_PATH)
    Observable<VideoListInfo> getVideoListInfo(@Query("category") String category,
                                               @Query("stage") String stage);

    //视频详情
    @GET(UrlHelper.Path.VIDEOINFO_PATH)
    Observable<VideoDetailsInfo> getVideoDetailsInfo(@Path("id") int id);
}
