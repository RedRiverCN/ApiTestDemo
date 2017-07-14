package com.red.apitestdemo;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.red.apitestdemo.db.GreenDaoHelper;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化数据库
        GreenDaoHelper.initDatabase(this, "videolist.db");
        GreenDaoHelper.enableQueryBuilderLog(); //开启调试 log
        //初始化Fresco图片库
        Fresco.initialize(this);
    }
}
