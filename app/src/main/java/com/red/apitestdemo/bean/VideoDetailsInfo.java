package com.red.apitestdemo.bean;

/**
 * Created by Red on 2017/7/6.
 */
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDetailsInfo {

    /**
     * code : 0
     * message : success
     * video :  {
         * id : 1
         * title : 恐龙快跑
         * ontime : 10:11
         * imgurl : /myres/bg0.jpg
         * mp4url : /myres/m.mp4
         * }
     * result : [
     * {
         * tab_title : 相关常识
         * data : [
             * {
             * img_url :
             * img_title : 疾病介绍
             * description : 要点描述
             * },
             * {
             * img_url :
             * img_title : 对症食疗
             * description : 要点描述
             * }
             * ]
        * ]
     *}
     */
    private int code;

    private String message;

    private VideoBean video;

    private List<TabBean> data;

    /**
     * id : 1
     * title : 恐龙快跑
     * ontime : 10:11
     * imgurl : /myres/bg0.jpg
     * mp4url : /myres/m.mp4
     */
    public static class VideoBean {

        private String id;//视频唯一id
        private String title;//标题
        private String ontime;//视频时长
        @SerializedName("imgurl")
        private String imgurl;//缩略图地址
        @SerializedName("mp4url")
        private String mp4url;//视频地址


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

    /**
     * tab_title : 相关常识
     * data : [
     * {
     * img_url :
     * img_title : 疾病介绍
     * description : 要点描述
     * },
     * {
     * img_url :
     * img_title : 对症食疗
     * description : 要点描述
     * }
     * ]
     */
    public static class TabBean {

        private String tab_title;

        private List<DataBean> data;

        /**
         * img_url :
         * img_title : 疾病介绍
         * description : 要点描述
         */
        public static class DataBean {

            private String img_url;//图地址

            private String img_title;//子标题

            private String description;//描述


            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getImg_title() {
                return img_title;
            }

            public void setImg_title(String img_title) {
                this.img_title = img_title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }


        public String getTab_title() {
            return tab_title;
        }

        public void setTab_title(String tab_title) {
            this.tab_title = tab_title;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
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

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<TabBean> getData() {
        return data;
    }

    public void setData(List<TabBean> data) {
        this.data = data;
    }
}

