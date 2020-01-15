package com.newtoncode.app.luckymonkeyhelper.base;

import android.content.Context;

import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.lib.ads.utils.PreferencesUtil;

/**
 * Created by next on 2017/1/21.
 */

public class AppSettings {

    public static final String SERVICE_OPEN_COUNT = "service_open_count";

    public static int getServiceOpenCount(Context context) {
        return PreferencesUtil.getInt(context, SERVICE_OPEN_COUNT + AppUtil.getVersionCode(context));
    }

    public static void setServiceOpenOnce(Context context) {
        int cacheCount = getServiceOpenCount(context);
        PreferencesUtil.putInt(context, SERVICE_OPEN_COUNT + AppUtil.getVersionCode(context), cacheCount + 1);
    }
}
