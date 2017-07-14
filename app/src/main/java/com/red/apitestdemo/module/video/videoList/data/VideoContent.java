package com.red.apitestdemo.module.video.videoList.data;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class VideoContent {

    private static VideoContent sVideoContent;

    /**
     * An array of items.
     */
    private List<VideoItem> mItems;

    /**
     * A map of items, by ID.
     */
    public static final Map<String, VideoItem> ITEM_MAP = new HashMap<>();



    public static VideoContent getInstance(Context context){
        if (sVideoContent == null){
            sVideoContent = new VideoContent(context);
        }
        return sVideoContent;
    }

    private VideoContent(Context context) {
        mItems = new ArrayList<>();
        //！1.查询数据库有无缓存数据，有则读，否则联网加载


    }


    public List<VideoItem> getItems() {
        return mItems;
    }

    public void setItems(List<VideoItem> items) {
        mItems = items;
    }

}
