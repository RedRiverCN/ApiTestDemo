package com.red.apitestdemo.utils;

import android.os.Environment;

/**
 * Created by Red on 2017/6/24.
 * 常量都放这
 */

public class ConstantUtil {
    public static final String SDPATH = Environment.getExternalStorageDirectory().getPath();

    public static final String TEMP = "/ihealth/temp/";

    public static final String VIDEO_TEMP_DIR = "/ihealth/temp/video/";

    public static final String THUMB_TEMP_DIR = "/ihealth/temp/thumb/";

    public static final String[] VIDEO_CATEGORY = {
            "stroke",//中风
            "officeAthletics",//办公室运动
            "childrenWithAutism",//小儿自闭症
            "brainParalysis",//脑瘫
            "other"
    };

    public static final String[] VIDEO_STAGE = {
            "early",
            "mid",
            "late",
    };

    public static String EXTRA_ID = "extra_id";

    public static String EXTRA_IMG_URL = "extra_img_url";
}
