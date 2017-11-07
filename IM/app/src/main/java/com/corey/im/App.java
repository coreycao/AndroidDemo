package com.corey.im;

import android.app.Application;

import com.corey.im.db.entity.DaoMaster;
import com.corey.im.db.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by sycao on 2017/11/6.
 */

public class App extends Application {
    public static final boolean ENCRYPTED = false;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        // do this once, for example in your Application class

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "im-db-encrypted" : "im-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
// do this in your activities/fragments to get hold of a DAO
//        noteDao = daoSession.getNoteDao();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
