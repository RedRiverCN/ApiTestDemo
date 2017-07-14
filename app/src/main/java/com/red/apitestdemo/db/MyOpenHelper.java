package com.red.apitestdemo.db;

import android.content.Context;
import android.util.Log;

import com.red.apitestdemo.bean.DaoMaster;
import org.greenrobot.greendao.database.Database;

/**
 * 管理数据库升级
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d("dbase","db version update from " + oldVersion + " to " + newVersion);

        switch (oldVersion) {
            case 1:
                //TODO: VideoListDao.createTable(db, true);

                // 加入新字段 score
               // db.execSQL("ALTER TABLE 'VIDEOLIST' ADD 'SCORE' TEXT;");

                break;
        }
    }
}
