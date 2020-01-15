package com.newtoncode.app.luckymonkeyhelper.manager;

import android.annotation.SuppressLint;
import android.content.Context;

import com.newtoncode.app.luckymonkeyhelper.config.AppConfig;
import com.newtoncode.app.luckymonkeyhelper.dao.DaoMaster;
import com.newtoncode.app.luckymonkeyhelper.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by jeanboy on 2016/12/9.
 */

public class DBManager {

    private Context context;

    private DaoSession daoSession;

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static DBManager instance = new DBManager();
    }

    private DBManager() {
    }

    public static DBManager getInstance() {
        return SingletonHolder.instance;
    }

    public void build(Context context) {
        this.context = context.getApplicationContext();
    }

    private DaoMaster getDaoMaster(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, AppConfig.DB_NAME, null);
        return new DaoMaster(helper.getWritableDatabase());
    }

    private DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            daoSession = getDaoMaster(context).newSession();
        }
        return daoSession;
    }


    public <T> long save(T t) {
        return getDaoSession(context).insert(t);
    }

    public <T> long update(T t) {
        return getDaoSession(context).insertOrReplace(t);
    }

    public <T> void delete(T t) {
        getDaoSession(context).delete(t);
    }

    public <T> void deleteAll(Class<T> clazz) {
        getDaoSession(context).deleteAll(clazz);
    }

    public <T> T getById(Class<T> clazz, Long id) {
        return getDaoSession(context).load(clazz, id);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        return getDaoSession(context).loadAll(clazz);
    }

    /**
     * @param clazz
     * @param where  ex: where begin_date_time >= ? AND end_date_time <= ?
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<T> clazz, String where, String... params) {
        return getDaoSession(context).queryRaw(clazz, where, params);
    }

    public <T> QueryBuilder<T> queryBuilder(Class<T> clazz) {
        return getDaoSession(context).queryBuilder(clazz);
    }
}
