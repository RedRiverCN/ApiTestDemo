package com.red.apitestdemo.module.video;

import android.content.Context;
import android.graphics.Bitmap;

import com.red.apitestdemo.base.BaseModel;
import com.red.apitestdemo.base.BasePresenter;
import com.red.apitestdemo.base.BaseView;
import com.red.apitestdemo.bean.VideoBean;

import io.reactivex.Observable;

/**
 * Created by Red on 2017/6/17.
 * 视频模块的契约类
 */

public class VideoContract {

    public interface Model extends BaseModel {
        Observable<VideoBean> getVideoInfo(int id);
        Observable<Bitmap> getVideoImg(Context context, String imgUrl);
    }

    public interface View extends BaseView {
        void onLoading();
        void onFinish();
        void onSuccess(VideoBean videoBean);
        void onError(String error);
        void onImgLoad(Bitmap bitmap);
        int getId();
        void setId(int id);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void playVideo();
        public abstract void getVideoImg(Context context, String imgUrl);

        public abstract void playImmediately();
    }
}