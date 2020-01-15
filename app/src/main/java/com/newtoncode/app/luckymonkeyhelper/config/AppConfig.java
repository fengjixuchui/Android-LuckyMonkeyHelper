package com.newtoncode.app.luckymonkeyhelper.config;

import android.content.Context;

import com.newtoncode.app.luckymonkeyhelper.utils.PreferencesUtil;

/**
 * Created by jeanboy on 2016/12/6.
 */

public class AppConfig {

    public static final String DB_NAME = "lucky_monkey_helper.db";

    private static final String KEY_MONKEY_RUSH_WAY = "key_monkey_rush_way";//默认抢红包方式停留在聊天界面

    public static boolean isAlwaysStayHome(Context context) {
        return PreferencesUtil.getBoolean(context, KEY_MONKEY_RUSH_WAY, false);
    }

    public static void setAlwaysStayHome(Context context, boolean alwaysStayHome) {
        PreferencesUtil.putBoolean(context, KEY_MONKEY_RUSH_WAY, alwaysStayHome);
    }

    private static final String KEY_ACTION_DELAY_TIME = "key_action_delay_time";
    private static final int DEFAULT_ACTION_DELAY_TIME = 2 * 1000;//默认延迟时间2s

    public static int getActionDelayTime(Context context) {
        return PreferencesUtil.getInt(context, KEY_ACTION_DELAY_TIME, DEFAULT_ACTION_DELAY_TIME);
    }

    public static void setActionDelayTime(Context context, int actionDelayTime) {
        PreferencesUtil.putInt(context, KEY_ACTION_DELAY_TIME, actionDelayTime);
    }
}
