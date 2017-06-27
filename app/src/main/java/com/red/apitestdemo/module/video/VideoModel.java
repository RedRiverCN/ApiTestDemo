package com.red.apitestdemo.module.video;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.red.apitestdemo.bean.VideoBean;
import com.red.apitestdemo.network.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Red on 2017/6/17.
 */

public class VideoModel implements VideoContract.Model {
    @Override
    public Observable<VideoBean> getVideoInfo(int id) {
        return RetrofitHelper.getVideoAPI()
                .getVideo(id);
    }

    @Override
    public Observable<Bitmap> getVideoImg(final Context context, final String imgUrl) {

        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = Glide.with(context)
                        .load(imgUrl)
                        .asBitmap()
                        .centerCrop()
                        .into(200, 200)
                        .get();
                if (bitmap!=null){
                    e.onNext(bitmap);
                    e.onComplete();
                }else {
                    e.onError(new RuntimeException());
                    e.onComplete();
                }

            }
        });

    }
}
