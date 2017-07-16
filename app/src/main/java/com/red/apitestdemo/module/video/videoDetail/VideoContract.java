package com.red.apitestdemo.module.video.videoDetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.red.apitestdemo.base.BaseModel;
import com.red.apitestdemo.base.BasePresenter;
import com.red.apitestdemo.base.BaseView;
import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.bean.VideoListInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Red on 2017/6/17.
 * 视频模块的契约类
 */

public class VideoContract {

    public interface Model extends BaseModel {
        Observable<VideoDetailsInfo> getVideoInfo(int id);
        //Observable<List<VideoDetailsInfo.TabBean>> getTabDetailsInfo(int id);
    }

    public interface View extends BaseView {
        void onLoading();
        void onFinish();
        void onSuccess(String videoURL, String title);
        void onError(String error);
        void setProgressBar(int progress);

        void toast(final String msg);
        void playImmediately();
        ImageView getThumbViewHolder();
        int getId();
        void setId(int id);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void playVideo();

        public abstract void playImmediately();
        public abstract void downloadFiles(@NonNull final String url,
                                           @NonNull final String saveName,
                                           @Nullable final String savePath);

        public abstract void viewOnDestroy();

        public abstract void startDownload();
    }
}
