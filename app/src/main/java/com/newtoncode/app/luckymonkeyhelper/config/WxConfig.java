package com.newtoncode.app.luckymonkeyhelper.config;

/**
 * Created by jeanboy on 2016/12/5.
 */

public class WxConfig {

    /**
     * 6.3.30
     */
    /*微信界面action一般不变*/
    public static final String monkeyCanOpen = "com.tencent.mm.ui.LauncherUI";//红包可点击
    public static final String monkeyOpened = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI";//红包可抢
    public static final String monkeyDetail = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";//红包详情

    /*微信包名红包名变动概率不大*/
    public static final String monkeyFlag = "[微信红包]";
    public static final String monkeyFlagSign = ": ";
    public static final String monkeyItemFlag = "领取红包";
    public static final String appPackageName = "com.tencent.mm";
    public static final String monkeyNotifyAction = "android.widget.ListView";//红包消息到来action
    public static final String textNotifyAction = "android.widget.TextView";//文本变化action
    public static final String inputNotifyAction = "android.widget.EditText";//输入内容变化action

    /*微信变动参数*/
    public static final String homeListViewId = "com.tencent.mm:id/big";//聊天记录列表listview id
    public static final String unreadId = "com.tencent.mm:id/hm";//聊天记录列表 未读消息数id
    public static final String descId = "com.tencent.mm:id/aef";//聊天记录列表 消息的内容id
    public static final String chatContainerId = "com.tencent.mm:id/cmy";//聊天界面
    public static final String chatContainerBackId = "com.tencent.mm:id/fz";//聊天界面back
    public static final String chatContainerTitleId = "com.tencent.mm:id/g1";//聊天界面titleId

    public static final String chatListViewId = "com.tencent.mm:id/a25";//聊天界面ListViewId
    public static final String chatItemId = "com.tencent.mm:id/o";//聊天消息ItemLayoutId
    public static final String chatAvatarId = "com.tencent.mm:id/i_";//聊天消息用户头像Id
    public static final String monkeyId = "com.tencent.mm:id/a4w";//红包layoutId
    //    public static final String monkeyIconId = "com.tencent.mm:id/a5j";//红包iconId
    public static final String monkeyCanOpenFlagId = "com.tencent.mm:id/a5l";//领取红包flagId
    public static final String monkeyOpenAction = "com.tencent.mm:id/bg7";//红包打开按钮
    public static final String monkeyBack = "com.tencent.mm:id/bga";//红包返回

    public static final String monkeyDetailBack = "com.tencent.mm:id/gd";//红包详情返回
    public static final String monkeyDetailAuthBack = "com.tencent.mm:id/boa";//红包详情认证返回
    public static final String monkeyDetailNickName = "com.tencent.mm:id/bdm";//红包详情用户昵称
    public static final String monkeyDetailMoney = "com.tencent.mm:id/bdq";//红包详情抢到金额

}
