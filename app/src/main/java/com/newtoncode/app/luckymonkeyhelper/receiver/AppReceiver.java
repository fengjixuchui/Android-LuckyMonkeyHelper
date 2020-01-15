package com.newtoncode.app.luckymonkeyhelper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.newtoncode.app.luckymonkeyhelper.config.WxConfig;
import com.newtoncode.app.luckymonkeyhelper.manager.WxConfigManager;

/**
 * author: 乔晓松
 * email: qiaoxiaosong@fotoable.com
 * time: 2017/1/6 13:15
 * describe:监听应用的安装或升级,更新微信配置数据
 */
public class AppReceiver extends BroadcastReceiver {

//    private final static String TAG = "AppReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (!TextUtils.isEmpty(packageName) && packageName.equals(WxConfig.appPackageName)) {
                WxConfigManager.getInstance().installWxApp();
            }
//            Log.e(TAG, "--------安装成功" + packageName);
//            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
//            String packageName = intent.getData().getSchemeSpecificPart();
//            Log.e(TAG, "--------替换成功" + packageName);
//            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (!TextUtils.isEmpty(packageName) && packageName.equals(WxConfig.appPackageName)) {
                WxConfigManager.getInstance().unInstallWxApp();
            }
//            Log.e(TAG, "--------卸载成功" + packageName);
//            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
        }
    }
}
