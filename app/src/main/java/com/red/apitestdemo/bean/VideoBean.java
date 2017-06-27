package com.red.apitestdemo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Red on 2017/6/17.
 */

public class VideoBean {


    /**
     * id : 1
     * title : 恐龙快跑
     * imgurl : /myres/bg0.jpg
     * mp4url : /myres/m.mp4
     */

    private String id;
    private String title;
    @SerializedName("imgurl")
    private String imgurl;
    @SerializedName("mp4url")
    private String mp4url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMp4url() {
        return mp4url;
    }

    public void setMp4url(String mp4url) {
        this.mp4url = mp4url;
    }
}
