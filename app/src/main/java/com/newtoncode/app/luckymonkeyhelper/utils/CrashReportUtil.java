package com.newtoncode.app.luckymonkeyhelper.utils;

import android.content.Context;
import android.text.TextUtils;

import com.newtoncode.app.luckymonkeyhelper.BuildConfig;
import com.newtoncode.app.luckymonkeyhelper.base.Constants;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * author: 乔晓松
 * time: 2016/12/7 15:15
 * describe: bugly抽象层
 */
public class CrashReportUtil {

    public static void initCrashReport(Context context) {
        //设置只有主进程上报异常数据
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setAppChannel("myChannel");  //设置渠道
//        strategy.setAppVersion("1.0.1");      //App的版本
//        strategy.setAppPackageName("com.tencent.xx");  //App的包名
//        strategy.setAppReportDelay(20000);   //Bugly会在启动10s后联网同步数据
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        //bugly 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        //输出详细的Bugly SDK的Log；每一条Crash都会被立即上报；自定义日志将会在Logcat中输出。
        //建议在测试阶段建议设置成true，发布时设置为false。
//        CrashReport.initCrashReport(context, Constants.BUGLY_APP_ID, BuildConfig.DEBUG);//仅初始化bugly
        Bugly.init(context, Constants.BUGLY_APP_ID, BuildConfig.DEBUG);//初始化bugly和自动升级

        //设置是否是开发设备
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
    }

    /**
     * 抛出自定义异常到bugly
     *
     * @param throwable
     */
    public static void postCatchedException(Throwable throwable) {
        CrashReport.postCatchedException(throwable);
    }

    /**
     * 提交日志到bugly
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        BuglyLog.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        BuglyLog.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        BuglyLog.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        BuglyLog.e(tag, msg);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
