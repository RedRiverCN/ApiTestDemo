package com.red.apitestdemo.module.video;

import android.content.Context;
import android.graphics.Bitmap;

import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.network.auxiliary.ApiConstants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Red on 2017/6/17.
 */

public class VideoPresenter extends VideoContract.Presenter {

    @Override
    public void playVideo() {

        //访问服务器获取数据
        mView.onLoading();

        mModel.getVideoInfo(mView.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        String videoUrl = ApiConstants.VIDEO_BASE_URL + videoBean.getMp4url();
                        String imgUrl = ApiConstants.VIDEO_BASE_URL + videoBean.getImgurl();
                        String title = videoBean.getTitle();

                        getVideoImg(mContext,imgUrl);
                        mView.onSuccess(videoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError("网络请求出错");
                    }

                    @Override
                    public void onComplete() {
                        mView.onFinish();
                    }
                });
}

    @Override
    public void getVideoImg(Context context, String imgUrl) {
        mModel.getVideoImg(context, imgUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mView.onImgLoad(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError("缩略图获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void playImmediately() {

    }
}
