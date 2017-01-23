package com.yobin.stee.greendaodemo02;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.guangda.dao.DaoSession;
import com.guangda.dao.UsersDao;

import java.util.List;

import greendao.Users;

/**
 * Created by yobin_he on 2017/1/22.
 * 用户操作类
 */

public class DbService {
    private static final String TAG = DbService.class.getSimpleName();
    private static DbService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private UsersDao usersDao;

    private DbService() {
    }

    public static DbService getInstance(Context context){
        if(instance == null){
            instance = new DbService();
            if(appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.usersDao = instance.mDaoSession.getUsersDao(); //通过DaoSession获取userDao
        }
        return instance;
    }
    /**
     * 根据用户id,取出用户信息
     * @param id           用户id
     * @return             用户信息
     */
    public Users loadNote(long id){
        if(!TextUtils.isEmpty(id+"")){
            return usersDao.load(id);
        }
        return null;
    }

    /**
     * 取出所有数据
     * @return      所有数据信息
     */
    public List<Users> loadAllNote(){
        return usersDao.loadAll();
    }
    /**
     * 生成按id倒排序的列表
     * @return      倒排数据
     */
    public List<Users> loadAllNoteByOrder(){
        return usersDao.queryBuilder().orderDesc(UsersDao.Properties.Id).list();
    }

    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<Users> queryNote(String where,String... params){
        return usersDao.queryRaw(where,params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public long saveNote(Users user){
        return usersDao.insertOrReplace(user);
    }
    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNoteLists(final List<Users> list){
        if(list==null || list.isEmpty()){
            return;
        }
        usersDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Users users = list.get(i);
                    usersDao.insertOrReplace(users);
                }
            }
        });
    }

    /**
     * 删除所有数据 
     */
    public void deleteAllNote(){
        usersDao.deleteAll();
    }

    /**
     * 根据id,删除数据
     * @param id      用户id
     */
    public void deleteNote(long id){
        usersDao.deleteByKey(id);
        Log.i(TAG, "deleteNote");
    }
    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(Users user){
        usersDao.delete(user);
    }

}
