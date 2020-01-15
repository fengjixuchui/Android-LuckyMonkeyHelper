package com.newtoncode.app.luckymonkeyhelper.manager;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.newtoncode.app.luckymonkeyhelper.BuildConfig;
import com.newtoncode.app.luckymonkeyhelper.base.Constants;
import com.newtoncode.app.luckymonkeyhelper.base.MainApplication;
import com.newtoncode.app.luckymonkeyhelper.config.ConfigBean;
import com.newtoncode.app.luckymonkeyhelper.config.WxConfig;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.FileUtil;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.onlineconfig.UmengOnlineConfigureListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * author: song
 * time: 2016/12/6 16:23
 * describe: 更新配置数据
 */
public class WxConfigManager {

    private static WxConfigManager INSTANCE;

    private HashMap<String, ConfigBean> configBeanMap;

    private String wxVersionName;
    private ConfigBean configBean;

    private WxConfigManager() {
    }

    public static WxConfigManager getInstance() {
        if (INSTANCE == null) {
            synchronized (WxConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WxConfigManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取微信配置id
     *
     * @param key
     * @return
     */
    public String getId(String key) {
        if (key == null) {
            return null;
        }
        if (key.equals(Constants.homeListViewId)) {
            if (configBean != null) {
                return configBean.getHomeListViewId();
            }
            return WxConfig.homeListViewId;
        } else if (key.equals(Constants.unreadId)) {
            if (configBean != null) {
                return configBean.getUnreadId();
            }
            return WxConfig.unreadId;
        } else if (key.equals(Constants.descId)) {
            if (configBean != null) {
                return configBean.getDescId();
            }
            return WxConfig.descId;
        } else if (key.equals(Constants.chatContainerId)) {
            if (configBean != null) {
                return configBean.getChatContainerId();
            }
            return WxConfig.chatContainerId;
        } else if (key.equals(Constants.chatContainerBackId)) {
            if (configBean != null) {
                return configBean.getChatContainerBackId();
            }
            return WxConfig.chatContainerBackId;
        } else if (key.equals(Constants.chatContainerTitleId)) {
            if (configBean != null) {
                return configBean.getChatContainerTitleId();
            }
            return WxConfig.chatContainerTitleId;
        } else if (key.equals(Constants.chatListViewId)) {
            if (configBean != null) {
                return configBean.getChatListViewId();
            }
            return WxConfig.chatListViewId;
        } else if (key.equals(Constants.chatItemId)) {
            if (configBean != null) {
                return configBean.getChatItemId();
            }
            return WxConfig.chatItemId;
        } else if (key.equals(Constants.chatAvatarId)) {
            if (configBean != null) {
                return configBean.getChatAvatarId();
            }
            return WxConfig.chatAvatarId;
        } else if (key.equals(Constants.monkeyId)) {
            if (configBean != null) {
                return configBean.getMonkeyId();
            }
            return WxConfig.monkeyId;
        } else if (key.equals(Constants.monkeyCanOpenFlagId)) {
            if (configBean != null) {
                return configBean.getMonkeyCanOpenFlagId();
            }
            return WxConfig.monkeyCanOpenFlagId;
        } else if (key.equals(Constants.monkeyOpenAction)) {
            if (configBean != null) {
                return configBean.getMonkeyOpenAction();
            }
            return WxConfig.monkeyOpenAction;
        } else if (key.equals(Constants.monkeyBack)) {
            if (configBean != null) {
                return configBean.getMonkeyBack();
            }
            return WxConfig.monkeyBack;
        } else if (key.equals(Constants.monkeyDetailBack)) {
            if (configBean != null) {
                return configBean.getMonkeyDetailBack();
            }
            return WxConfig.monkeyDetailBack;
        } else if (key.equals(Constants.monkeyDetailAuthBack)) {
            if (configBean != null) {
                return configBean.getMonkeyDetailAuthBack();
            }
            return WxConfig.monkeyDetailAuthBack;
        } else if (key.equals(Constants.monkeyDetailNickName)) {
            if (configBean != null) {
                return configBean.getMonkeyDetailNickName();
            }
            return WxConfig.monkeyDetailNickName;
        } else if (key.equals(Constants.monkeyDetailMoney)) {
            if (configBean != null) {
                return configBean.getMonkeyDetailMoney();
            }
            return WxConfig.monkeyDetailMoney;
        }
        return null;
    }

    /**
     * 加载本地默认配置文件
     */
    public void loadLocalConfigData() {
        String wxVersionName2 = AppUtil.getVersionName(MainApplication.getInstance(), WxConfig.appPackageName);
        if (TextUtils.equals(wxVersionName, wxVersionName2) && configBean != null) {
            return;
        }
        wxVersionName = wxVersionName2;

        configBean = null;

        //读取友盟的在线参数缓存
        readWxConfigData(0);

        //友盟缓存不存在就读取默认配置列表
        if (configBeanMap == null) {
            String versionListData = FileUtil.readAsset(MainApplication.getInstance(), "version_list.data");
            if (TextUtils.isEmpty(versionListData)) {
                return;
            }
            configBeanMap = JSON.parseObject(versionListData, new TypeReference<HashMap<String, ConfigBean>>() {
            });
        }
        if (configBean == null) {
            loadCurrentVersion();
        }
    }

    private void loadCurrentVersion() {
        if (configBeanMap != null) {
            configBean = configBeanMap.get(wxVersionName);
        }
    }

    public void installWxApp() {
        loadLocalConfigData();
    }

    public void unInstallWxApp() {
        loadLocalConfigData();
    }

    /**
     * 加载友盟数据
     */
    public void loadUmengOnLineParams() {
        OnlineConfigAgent.getInstance().setDebugMode(BuildConfig.DEBUG);
        UmengOnlineConfigureListener configureListener = new UmengOnlineConfigureListener() {
            @Override
            public void onDataReceived(JSONObject json) {
                Log.e("qxs json", "" + json);
                readWxConfigData(1);
            }
        };
        OnlineConfigAgent.getInstance().setOnlineConfigListener(configureListener);
        OnlineConfigAgent.getInstance().updateOnlineConfig(MainApplication.getInstance());
    }

    /**
     * 读取友盟数据
     *
     * @param source
     */
    private void readWxConfigData(int source) {
        try {
            wxVersionName = AppUtil.getVersionName(MainApplication.getInstance(), WxConfig.appPackageName);
            if (TextUtils.isEmpty(wxVersionName)) {
                return;
            }
            String configData = OnlineConfigAgent.getInstance().getConfigParams(MainApplication.getInstance(), wxVersionName);
            if (!TextUtils.isEmpty(configData)) {
                ConfigBean configBean1 = JSON.parseObject(configData, ConfigBean.class);
                if (configBean1 == null || source == 1) {
                    configBean = configBean1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSupport() {
        if (configBean == null) {
            return false;
        } else {
            return true;
        }
    }
}
