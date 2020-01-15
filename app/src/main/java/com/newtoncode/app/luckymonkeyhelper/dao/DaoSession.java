package com.newtoncode.app.luckymonkeyhelper.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.newtoncode.app.luckymonkeyhelper.model.LuckyMonkeyModel;

import com.newtoncode.app.luckymonkeyhelper.dao.LuckyMonkeyModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig luckyMonkeyModelDaoConfig;

    private final LuckyMonkeyModelDao luckyMonkeyModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        luckyMonkeyModelDaoConfig = daoConfigMap.get(LuckyMonkeyModelDao.class).clone();
        luckyMonkeyModelDaoConfig.initIdentityScope(type);

        luckyMonkeyModelDao = new LuckyMonkeyModelDao(luckyMonkeyModelDaoConfig, this);

        registerDao(LuckyMonkeyModel.class, luckyMonkeyModelDao);
    }
    
    public void clear() {
        luckyMonkeyModelDaoConfig.clearIdentityScope();
    }

    public LuckyMonkeyModelDao getLuckyMonkeyModelDao() {
        return luckyMonkeyModelDao;
    }

}