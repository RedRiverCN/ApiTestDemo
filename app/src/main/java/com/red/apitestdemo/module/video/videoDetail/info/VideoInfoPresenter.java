package com.red.apitestdemo.module.video.videoDetail.info;

import com.red.apitestdemo.bean.VideoDetailsInfo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Red on 2017/7/6.
 */

public class VideoInfoPresenter extends VideoInfoContract.VideoInfoPresenter {
    @Override
    public void loadData() {
        mModel.getVideoDetailsInfo(mView.getVId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoDetailsInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoDetailsInfo DataBean) {

                        mView.loadData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
