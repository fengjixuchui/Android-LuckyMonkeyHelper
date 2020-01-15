package com.newtoncode.app.luckymonkeyhelper.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.newtoncode.app.luckymonkeyhelper.base.Constants;
import com.newtoncode.app.luckymonkeyhelper.config.WxConfig;
import com.newtoncode.app.luckymonkeyhelper.manager.DBManager;
import com.newtoncode.app.luckymonkeyhelper.manager.WxConfigManager;
import com.newtoncode.app.luckymonkeyhelper.model.LuckyMonkeyModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeanboy on 2016/12/5.
 */

public class LuckyMonkeyUtil {

    private static final String TAG = LuckyMonkeyUtil.class.getSimpleName();
    private static CharSequence chatTitle;
    private static long actionLatestTime;
    private static boolean isHelperClicked = false;
    private static Map<String, Long> monkeyCacheMap = new HashMap<>();

    public static boolean isUserInput(AccessibilityEvent event) {
        if (event == null) return false;
        return WxConfig.inputNotifyAction.equals(event.getClassName());
    }

    public static boolean isMonkeyCome(AccessibilityEvent event) {
        if (event == null) return false;
        return WxConfig.monkeyNotifyAction.equals(event.getClassName());
    }

    /**
     * 检查通知栏是否有红包
     *
     * @param event
     */
    public static void checkNotification(AccessibilityEvent event) {
        try {
            if (event == null) return;
            List<CharSequence> textList = event.getText();
            if (!textList.isEmpty()) {
                for (CharSequence text : textList) {
                    String content = text.toString();
                    if (content.contains(WxConfig.monkeyFlag)) {
                        //判断是否是微信红包开头或者微信红包前面有:，防误点
                        int index = content.indexOf(WxConfig.monkeyFlag);
                        boolean isHaveSign = false;
                        if (index > 1) {
                            isHaveSign = WxConfig.monkeyFlagSign.equals(content.substring(index - 2, index));
                        }
                        //红包信息长度判断
                        if ((index == 0 || isHaveSign) && (index + WxConfig.monkeyFlag.length()) < content.length()) {
                            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                pendingIntent.send();
                                Log.e(TAG, "====发现红包踪迹==进入微信=====");
                                return;
                            }
                        } else {
                            Log.e(TAG, "误导信息," + content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkClickIsMonkey(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) return false;
        return nodeInfo.toString().equals(WxConfig.monkeyItemFlag);
    }

    /**
     * 检查home page是否有红包消息
     *
     * @param nodeInfo
     * @return
     */
    public static boolean scanMonkeyFromHome(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) return false;
        List<AccessibilityNodeInfo> containerNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.homeListViewId));
        if (containerNodeList != null && containerNodeList.size() > 0) {//获取容器节点
            AccessibilityNodeInfo containerNode = containerNodeList.get(0);
            for (int i = 0; i < containerNode.getChildCount(); i++) {//遍历子节点，获取item
                AccessibilityNodeInfo item = containerNode.getChild(i);
                if (item != null) {
                    List<AccessibilityNodeInfo> unreadNode = item.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.unreadId));
                    List<AccessibilityNodeInfo> descNode = item.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.descId));
                    if (unreadNode.size() > 0 && descNode.size() > 0) {//如果存在未读消息，且描述不为空，可能有红包
                        Log.e(TAG, "====scanMonkeyFromChat====" + unreadNode.get(0).getText() + "==" + descNode.get(0).getText());
                        CharSequence descText = descNode.get(0).getText();
                        if (descText != null && String.valueOf(descText).contains(WxConfig.monkeyFlag)) {//如果包含红包flag说明有红包
                            String content = String.valueOf(descText);
                            //判断是否是微信红包开头或者微信红包前面有:，防误点
                            int index = content.indexOf(WxConfig.monkeyFlag);
                            boolean isHaveSign = false;
                            if (index > 1) {
                                isHaveSign = WxConfig.monkeyFlagSign.equals(content.substring(index - 2, index));
                            }
                            //红包信息长度判断
                            if ((index == 0 || isHaveSign) && (index + WxConfig.monkeyFlag.length()) < content.length()) {
                                Log.e(TAG, "======发现红包==点击去抢===");
                                AccessibilityNodeUtil.doClick(item);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 扫描红包是否出现
     *
     * @param nodeInfo
     */
    public static void scanMonkeyFromChat(AccessibilityNodeInfo nodeInfo, long actionTimeDelay) {
//        if (nodeInfo == null) return;
//        Log.e(TAG, "===聊天界面=====" + chatTitle);
//
//        boolean isTimeReady = false;
//        long nowTime = System.currentTimeMillis();
//        if ((nowTime - actionLatestTime) > actionTimeDelay) {
//            isTimeReady = true;
//        }
//        Log.e(TAG, "===聊天界面=====" + chatTitle + "===isTimeReady==" + isTimeReady + "==delay==" + (nowTime - actionLatestTime));
//        boolean canDoFind = false;
//        CharSequence chatNowTitle = getChatTitle(nodeInfo);
//        if (chatNowTitle != null && !chatNowTitle.equals(chatTitle)) {
//            chatTitle = chatNowTitle;
//            canDoFind = true;
//        }
//
//        Log.e(TAG, "===聊天界面=====" + chatTitle + "===canDoFind==" + canDoFind + "==chatNowTitle==" + chatNowTitle);
//        if (canDoFind) {
//            Log.e(TAG, "===聊天界面=====" + chatTitle + "===canDoFind==");
//            findFromChatToOpen(nodeInfo);
//        } else if (isTimeReady) {
//            Log.e(TAG, "===聊天界面=====" + chatTitle + "===isTimeReady==");
//            findFromChatToOpen(nodeInfo);
//        }

        findFromChatToOpen(nodeInfo, actionTimeDelay);
    }

    /**
     * 判断当前页面是否是聊天界面
     *
     * @param nodeInfo
     * @return
     */
    public static boolean isChatPage(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> chatContainerNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatContainerId));
            if (chatContainerNodeList != null && chatContainerNodeList.size() > 0) {//获取容器节点
                AccessibilityNodeInfo chatContainerNode = chatContainerNodeList.get(0);
                return chatContainerNode.isFocusable() && chatContainerNode.isEnabled();//聊天页面是否显示
            }
        }
        return false;
    }

    public static int getChatHeight(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> chatListViewNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatListViewId));
            if (chatListViewNodeList != null && chatListViewNodeList.size() > 0) {
                AccessibilityNodeInfo chatListViewNode = chatListViewNodeList.get(0);
                Rect outBounds = new Rect();
                chatListViewNode.getBoundsInScreen(outBounds);
                Log.e(TAG, "===获取到的聊天界面的高度==outBounds==" + outBounds.toString() + "===" + outBounds.bottom);
                return outBounds.bottom;
            }
        }
        return 0;
    }

    /**
     * 获取当前聊天页面title
     *
     * @param nodeInfo
     * @return
     */
    public static CharSequence getChatTitle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> chatTitleNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatContainerTitleId));
            if (chatTitleNodeList != null && chatTitleNodeList.size() > 0) {//是否有title
                AccessibilityNodeInfo chatTitleNode = chatTitleNodeList.get(0);//取出title Node
                return chatTitleNode.getText();
            }
        }
        return null;
    }

    /**
     * 找出聊天界面红包并打开
     *
     * @param nodeInfo
     */
    public static void findFromChatToOpen(AccessibilityNodeInfo nodeInfo, long actionTimeDelay) {
        if (nodeInfo == null) return;

        List<AccessibilityNodeInfo> chatItemNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatItemId));
        Log.e(TAG, "===findFromChatToOpen==chatItemNodeList===" + chatItemNodeList.size());
        if (chatItemNodeList.size() > 0) {

            AccessibilityNodeInfo chatItemNode = chatItemNodeList.get(chatItemNodeList.size() - 1);//取出最后一条item
            String cacheKey = String.valueOf(chatItemNode.hashCode());

            for (int i = 0; i < chatItemNode.getChildCount(); i++) {
                AccessibilityNodeInfo chatItemChildNode = chatItemNode.getChild(i);
                if (chatItemChildNode != null) {
                    List<AccessibilityNodeInfo> monkeyNodeList = chatItemChildNode.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyId));
                    Log.e(TAG, "===findFromChatToOpen==monkeyNodeList===" + monkeyNodeList.size());

                    if (monkeyNodeList.size() > 0) {//是否有红包
                        AccessibilityNodeInfo monkeyNode = monkeyNodeList.get(monkeyNodeList.size() - 1);//取出红包
                        List<AccessibilityNodeInfo> monkeyCanOpenNodeList =
                                monkeyNode.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyCanOpenFlagId));

                        Log.e(TAG, "===findFromChatToOpen==monkeyCanOpenNodeList===" + monkeyCanOpenNodeList.size());

                        if (monkeyNode.isClickable() && monkeyCanOpenNodeList.size() > 0) {//包含可领取的红包
                            cacheKey += String.valueOf(monkeyNode.hashCode());

                            //判断是否是领取红包文本
                            String fetchRedPackageSign = monkeyCanOpenNodeList.get(0).getText().toString();
                            if (WxConfig.monkeyItemFlag.equals(fetchRedPackageSign)) {
                                if (monkeyCacheMap.get(cacheKey) == null) {
                                    monkeyCacheMap.put(cacheKey, System.currentTimeMillis());
                                    Log.e(TAG, "===找到红包==点击打开===" + cacheKey);
                                    AccessibilityNodeUtil.doClick(monkeyNode);
                                } else {
                                    long cacheTime = monkeyCacheMap.get(cacheKey);
                                    if ((System.currentTimeMillis() - cacheTime) > actionTimeDelay) {
                                        monkeyCacheMap.put(cacheKey, System.currentTimeMillis());
                                        Log.e(TAG, "===找到红包=超时=点击打开===" + cacheKey);
                                        AccessibilityNodeUtil.doClick(monkeyNode);
                                    }
                                }
                            } else {
                                Log.e(TAG, "非红包：" + fetchRedPackageSign);
                            }
                        }
                    } else {
                        List<AccessibilityNodeInfo> avatarNodeList = chatItemChildNode.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatAvatarId));
                        if (avatarNodeList.size() > 0) {
                            AccessibilityNodeInfo avatarNode = avatarNodeList.get(avatarNodeList.size() - 1);//取出发红包的用户头像
                            cacheKey += avatarNode.getContentDescription();
                            Log.e(TAG, "===找到红包==用户头像===" + avatarNode.getContentDescription());
                        }
                    }
                }
            }
        }
    }

    /**
     * 拆红包
     *
     * @param nodeInfo
     */
    public static void getMonkeyMoney(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> monkeyOpenActionNodeList =
                    nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyOpenAction));
            if (monkeyOpenActionNodeList != null && monkeyOpenActionNodeList.size() > 0) {//是否有拆红包按钮
                AccessibilityNodeInfo monkeyOpenActionNode = monkeyOpenActionNodeList.get(0);
                if (monkeyOpenActionNode.isClickable()) {//点击拆红包
                    Log.e(TAG, "===拆红包=====");
                    isHelperClicked = true;
                    AccessibilityNodeUtil.doClick(monkeyOpenActionNode);
                }
            } else {
                Log.e(TAG, "===红包不可抢 返回=====");
                doLuckyMonkeyBack(nodeInfo);
            }
        }
    }

    /**
     * 获取抢到的红包的详情内容
     *
     * @param nodeInfo
     */
    public static void getMonkeyDetail(AccessibilityNodeInfo nodeInfo) {
        if (isHelperClicked && nodeInfo != null) {
            String monkeyMoney = "";
            String monkeyNickName = "";
            List<AccessibilityNodeInfo> monkeyDetailMoneyNodeList =
                    nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyDetailMoney));
            if (monkeyDetailMoneyNodeList.size() > 0) {//抢到红包金额
                AccessibilityNodeInfo monkeyDetailMoneyNode = monkeyDetailMoneyNodeList.get(0);
                monkeyMoney = monkeyDetailMoneyNode.getText().toString();
            }

            List<AccessibilityNodeInfo> monkeyDetailNickNameNodeList =
                    nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyDetailNickName));

            if (monkeyDetailNickNameNodeList.size() > 0) {//发红包的用户昵称
                AccessibilityNodeInfo monkeyDetailNickNameNode = monkeyDetailNickNameNodeList.get(0);
                monkeyNickName = monkeyDetailNickNameNode.getText().toString();
            }
            // TODO: 2016/12/9 数据库缓存
            Log.e(TAG, "=====抢到红包详情====monkeyNickName==" + monkeyNickName + "====monkeyMoney===" + monkeyMoney);
            LuckyMonkeyModel luckyMonkeyModel = new LuckyMonkeyModel();
            try {
                double money = Double.valueOf(monkeyMoney);
                luckyMonkeyModel.setMoney((int) (money * 100));
            } catch (NumberFormatException e) {
                luckyMonkeyModel.setMoney(1);
            }
            luckyMonkeyModel.setUserNick(monkeyNickName);
            luckyMonkeyModel.setCreateTime(System.currentTimeMillis());
            long saveId = DBManager.getInstance().save(luckyMonkeyModel);

            Log.e(TAG, "=====抢到红包已存入数据库===saveId==" + saveId);
            isHelperClicked = false;
        }
    }

    /**
     * 更新最后一次操作红包的时间
     */
    public static void updateLatestTime() {
        actionLatestTime = System.currentTimeMillis();
    }

    /**
     * 聊天界面返回到主界面
     *
     * @param nodeInfo
     */
    public static void doChatBackToHome(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) return;
        if (isChatPage(nodeInfo)) {
            List<AccessibilityNodeInfo> chatBackNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.chatContainerBackId));
            if (chatBackNodeList != null && chatBackNodeList.size() > 0) {//获取容器节点
                AccessibilityNodeInfo chatBackNode = chatBackNodeList.get(0);
                if (chatBackNode.isClickable() && chatBackNode.isEnabled()) {
                    AccessibilityNodeUtil.doClick(chatBackNode);
                }
            }
        }
    }

    /**
     * 红包不可抢返回
     *
     * @param nodeInfo
     */
    public static void doLuckyMonkeyBack(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> monkeyBackNodeList = nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyBack));
            if (monkeyBackNodeList != null && monkeyBackNodeList.size() > 0) {
                AccessibilityNodeInfo monkeyBackNode = monkeyBackNodeList.get(0);
                if (monkeyBackNode.isClickable()) {
                    AccessibilityNodeUtil.doClick(monkeyBackNode);
                    updateLatestTime();
                }
            }
        }
    }

    /**
     * 红包详情返回
     *
     * @param nodeInfo
     */
    public static void doLuckyMonkeyDetailBack(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> monkeyDetailBackNodeList =
                    nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyDetailBack));
            if (monkeyDetailBackNodeList != null && monkeyDetailBackNodeList.size() > 0) {
                AccessibilityNodeInfo monkeyDetailBackNode = monkeyDetailBackNodeList.get(0);
                if (monkeyDetailBackNode.isClickable()) {
                    AccessibilityNodeUtil.doClick(monkeyDetailBackNode);
                }
            }
        }
    }

    /**
     * 关闭实名认证窗口
     *
     * @param nodeInfo
     */
    public static void doLuckyMonkeyDetailAuthBack(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> monkeyDetailAuthBackNodeList =
                    nodeInfo.findAccessibilityNodeInfosByViewId(WxConfigManager.getInstance().getId(Constants.monkeyDetailAuthBack));
            if (monkeyDetailAuthBackNodeList != null && monkeyDetailAuthBackNodeList.size() > 0) {
                AccessibilityNodeInfo monkeyDetailAuthBackNode = monkeyDetailAuthBackNodeList.get(0);
                if (monkeyDetailAuthBackNode.isClickable()) {
                    AccessibilityNodeUtil.doClick(monkeyDetailAuthBackNode);
                }
            }
        }
    }


}
