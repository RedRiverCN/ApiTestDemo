package com.red.apitestdemo.db;

import android.content.Context;

import com.red.apitestdemo.bean.DaoMaster;
import com.red.apitestdemo.bean.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 用于初始化以及获取DaoMaster和DaoSession
 */
public class GreenDaoHelper {
    private static final String DEFAULT_DB_NAME = "default.db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static Context mContext;
    private static String DB_NAME;

    public static void initDatabase(Context context) {
        initDatabase(context, DEFAULT_DB_NAME);
    }

    public static void initDatabase(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
    }
    //获取数据库对象
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new MyOpenHelper(mContext, DB_NAME);
            daoMaster = new DaoMaster(helper.getEncryptedReadableDb("password1233456")/*获取加密数据库*/);
        }
        return daoMaster;
    }
    //获取Dao对象管理者
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static void enableQueryBuilderLog(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
