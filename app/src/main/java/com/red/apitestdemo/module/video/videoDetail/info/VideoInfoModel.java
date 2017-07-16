package com.red.apitestdemo.module.video.videoDetail.info;

import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.network.RetrofitHelper;

import io.reactivex.Observable;

/**
 * Created by Red on 2017/7/6.
 */

public class VideoInfoModel implements VideoInfoContract.VideoInfoModel{

    @Override
    public Observable<VideoDetailsInfo> getVideoDetailsInfo(int id) {
        return RetrofitHelper.getInstance().getVideoAPI()
                .getVideoDetailsInfo(id);
    }


}
