package com.yobin.stee.greendaodemo02;

import android.app.Application;
import android.content.Context;

import com.guangda.dao.DaoMaster;
import com.guangda.dao.DaoSession;

/**
 * Created by yobin_he on 2017/1/22.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if(mInstance == null){
            mInstance = this;
        }
    }
    /**
     * 取得DaoMaster
     *
     * @param context        上下文
     * @return               DaoMaster
     */

    public static DaoMaster getDaoMaster(Context context){
        if(daoMaster==null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,"myDb",null);
            daoMaster = new DaoMaster(helper.getWritableDatabase()); //通过helper获取DaoMaster实例;
        }
        return daoMaster;
    }
    /**
     * 取得DaoSession
     *
     * @param context        上下文
     * @return               DaoSession
     */
    public static DaoSession getDaoSession(Context context){
        if(daoSession == null){
            if(daoMaster==null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();//通过daoMaster.newSession，得到daoSession的实例
        }
        return daoSession;
    }
}
