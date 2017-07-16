package com.red.apitestdemo.module.video.videoList.data;

import android.net.Uri;

/**
 * the model
 */
public class VideoItem {
    private int mVideoId;//视频ID
    private Uri mThumbUri;//缩略图地址
    private String mTitle;//标题
    private String mTime;//时长
    private String mCategory;
    private String mStage;




    public VideoItem(int videoId, Uri thumbUri, String title, String time, String category, String stage) {
        mVideoId = videoId;
        mThumbUri = thumbUri;
        mTitle = title;
        mTime = time;
        mCategory = category;
        mStage = stage;
    }

    //region getter and setter
    public int getVideoId() {
        return mVideoId;
    }

    public void setVideoId(int videoId) {
        mVideoId = videoId;
    }

    public Uri getThumbUri() {
        return mThumbUri;
    }

    public void setThumbUri(Uri thumbUri) {
        mThumbUri = thumbUri;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getStage() {
        return mStage;
    }

    public void setStage(String stage) {
        mStage = stage;
    }
    //endregion
}
