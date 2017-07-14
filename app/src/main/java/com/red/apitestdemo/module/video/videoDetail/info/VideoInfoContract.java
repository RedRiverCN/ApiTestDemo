package com.red.apitestdemo.module.video.videoDetail.info;

import com.red.apitestdemo.base.BaseModel;
import com.red.apitestdemo.base.BasePresenter;
import com.red.apitestdemo.base.BaseView;
import com.red.apitestdemo.bean.VideoDetailsInfo;

import io.reactivex.Observable;

/**
 * Created by Red on 2017/7/6.
 */

public class VideoInfoContract {
    public interface VideoInfoModel extends BaseModel {
        Observable<VideoDetailsInfo> getVideoDetailsInfo(int id);
    }

    public interface VideoInfoView extends BaseView {
        void loadData();
        int getVId();
        void setVId(int id);
    }

    public abstract static class VideoInfoPresenter extends BasePresenter<VideoInfoView, VideoInfoModel> {
        public abstract void loadData();

    }
}
