package com.red.apitestdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Red on 2017/7/13.
 * 更多视频列表 模型类
 */

public class VideoListInfo {

    /**
     * code : 0
     * message : success
     * result : [
     * {
     * "id":2,
     * "title":"这是标题",
     * "ontime":"10:11",
     * "category":"stroke",
     * "stage":"early",
     * "cover_url":"/myres/bg0.jpg",
     * "mp4_url":"/myres/xx.mp4"
     * },
     * {
     * "id":2,
     * "title":"这是标题",
     * "ontime":"10:11",
     * "category":"officeAthletics",
     * "stage":"mid",
     * "cover_url":"/myres/bg0.jpg",
     * "mp4_url":"/myres/xx.mp4"
     * }
     * ]
     */
    private int code;

    private String message;

    private List<ResultBean> result;

    public static class ResultBean {

        private String id;//视频id
        private String title;//标题
        private String ontime;//视频时长s
        private String category;//所属类别
        private String stage;//阶段
        private String cover_url;//封面地址
        private String mp4_url;//视频地址



        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

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

        public String getOntime() {
            return ontime;
        }

        public void setOntime(String ontime) {
            this.ontime = ontime;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }


        public String getMp4_url() {
            return mp4_url;
        }

        public void setMp4_url(String mp4_url) {
            this.mp4_url = mp4_url;
        }

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

}
