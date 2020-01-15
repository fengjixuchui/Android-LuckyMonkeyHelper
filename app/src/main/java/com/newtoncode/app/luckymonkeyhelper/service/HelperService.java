package com.newtoncode.app.luckymonkeyhelper.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.newtoncode.app.luckymonkeyhelper.config.AppConfig;
import com.newtoncode.app.luckymonkeyhelper.config.WxConfig;
import com.newtoncode.app.luckymonkeyhelper.utils.LuckyMonkeyUtil;

/**
 * Created by jeanboy on 2016/12/5.
 */

public class HelperService extends AccessibilityService {

    private static final String TAG = HelperService.class.getSimpleName();
    private boolean isNotificationAction = false;
    private boolean isDetailBack = false;
    private boolean isAutoFromHome = false;
    private boolean isUserInput = false;
    private boolean isUserClick = false;
    private int listViewHeight = 0;

    /**
     * 服务启动时调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "===红包助手服务启用===");
    }

    /**
     * 监听窗口变化回调
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.e(TAG, "===红包助手服务窗口变化==eventType=" + eventType);
        switch (eventType) {
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.e(TAG, "==TYPE_NOTIFICATION_STATE_CHANGED==getClassName===" + event.getClassName());
                Log.e(TAG, "==TYPE_NOTIFICATION_STATE_CHANGED==getPackageName===" + event.getPackageName());
                Log.e(TAG, "==TYPE_NOTIFICATION_STATE_CHANGED==toString===" + event.toString());
                isNotificationAction = true;
                LuckyMonkeyUtil.checkNotification(event);
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://打开popupwindow,菜单，对话框时候会触发
                Log.e(TAG, "==TYPE_WINDOW_STATE_CHANGED==getClassName===" + event.getClassName());
                Log.e(TAG, "==TYPE_WINDOW_STATE_CHANGED==getPackageName===" + event.getPackageName());
                Log.e(TAG, "==TYPE_WINDOW_STATE_CHANGED==toString===" + event.toString());
                if (WxConfig.monkeyCanOpen.equals(event.getClassName())) {
                    Log.e(TAG, "======红包可点击====");
                    if (isNotificationAction) {
                        isNotificationAction = false;
                        LuckyMonkeyUtil.findFromChatToOpen(getRootInActiveWindow(), AppConfig.getActionDelayTime(getApplicationContext()));
                    }
                    //是否需要返回主界面
                    if (isDetailBack) {
                        isDetailBack = false;
                        if (AppConfig.isAlwaysStayHome(getApplicationContext())) {
                            LuckyMonkeyUtil.doChatBackToHome(getRootInActiveWindow());
                        }
                    }
                } else if (WxConfig.monkeyOpened.equals(event.getClassName())) {
                    Log.e(TAG, "======红包可抢====");
                    LuckyMonkeyUtil.getMonkeyMoney(getRootInActiveWindow());
                    isUserClick = false;
                } else if (WxConfig.monkeyDetail.equals(event.getClassName())) {
                    Log.e(TAG, "======红包详情页====");
                    LuckyMonkeyUtil.getMonkeyDetail(getRootInActiveWindow());
                    LuckyMonkeyUtil.doLuckyMonkeyDetailBack(getRootInActiveWindow());
                    isDetailBack = true;
                }
                LuckyMonkeyUtil.updateLatestTime();
                break;

            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://子view变化
                Log.e(TAG, "==TYPE_WINDOW_CONTENT_CHANGED==getClassName===" + event.getClassName());
                Log.e(TAG, "==TYPE_WINDOW_CONTENT_CHANGED==getPackageName===" + event.getPackageName());
                Log.e(TAG, "==TYPE_WINDOW_CONTENT_CHANGED==toString===" + event.toString());

                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                if (isDetailBack) {//如果有实名认证窗口关闭
                    LuckyMonkeyUtil.doLuckyMonkeyDetailAuthBack(nodeInfo);
                }

                if (isUserClick) return;//如果是用户点击红包

                if (LuckyMonkeyUtil.isChatPage(nodeInfo)) {

                    int nowHeight = LuckyMonkeyUtil.getChatHeight(nodeInfo);

                    if (listViewHeight == 0 || nowHeight == listViewHeight) {
                        isUserInput = false;
                    }

                    if (nowHeight > listViewHeight) {
                        listViewHeight = nowHeight;
                    }

                    Log.e(TAG, "====listViewHeight==" + listViewHeight + "===nowHeight==" + nowHeight +
                            "==isUserInput==" + isUserInput +
                            "===isAutoFromHome==" + isAutoFromHome);
                    if (isAutoFromHome || !isUserInput) {
                        Log.e(TAG, "======可自动抢红包====scanMonkeyFromChat======");
                        LuckyMonkeyUtil.scanMonkeyFromChat(nodeInfo, AppConfig.getActionDelayTime(getApplicationContext()));
                    }
                } else {
                    isUserInput = false;
                    isAutoFromHome = LuckyMonkeyUtil.scanMonkeyFromHome(nodeInfo);
                }

                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED://显示窗口变化
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED://view获取焦点表示输入
                isUserInput = LuckyMonkeyUtil.isUserInput(event);
                break;

            case AccessibilityEvent.TYPE_VIEW_CLICKED://view点击
                isUserClick = LuckyMonkeyUtil.checkClickIsMonkey(event.getSource());
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                isUserInput = LuckyMonkeyUtil.isUserInput(event);
                break;
        }
    }

    /**
     * 中断服务
     */
    @Override
    public void onInterrupt() {
        Log.e(TAG, "===红包助手服务中断===");
    }
}
