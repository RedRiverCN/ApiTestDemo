package com.red.apitestdemo.network.auxiliary;

import android.os.Environment;

/**
 * Created by Red on 2017/6/17.
 */

public class UrlHelper {

    public static class Path{

        //视频api(不包含域名部分)
        public static final String VIDEO_FIND_1 = "video/find/1"
                ;

        public static final String VIDEO_LIST_PATH="demo/video/find/list";

        public static final String VIDEOINFO_PATH="demo/video/find/{id}/info";
    }

    public static class Key{
        public static final String V="v";
    }

    public static class DefaultValue{

        public static final String V="34";
    }
}