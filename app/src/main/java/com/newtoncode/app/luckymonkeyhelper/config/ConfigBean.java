package com.newtoncode.app.luckymonkeyhelper.config;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: 乔晓松
 * time: 2016/12/6 17:27
 * describe:
 */
public class ConfigBean implements Parcelable {

    public String homeListViewId;//聊天记录列表listview id
    public String unreadId;//聊天记录列表 未读消息数id
    public String descId;//聊天记录列表 消息的内容id
    public String chatContainerId;//聊天界面
    public String chatContainerBackId;//聊天界面back
    public String chatContainerTitleId;//聊天界面titleId

    public String chatListViewId;//聊天界面ListViewId
    public String chatItemId;//聊天消息ItemLayoutId
    public String chatAvatarId;//聊天消息用户头像Id
    public String monkeyId;//红包layoutId
    public String monkeyCanOpenFlagId;//领取红包flagId
    public String monkeyOpenAction;//红包打开按钮
    public String monkeyBack;//红包返回

    public String monkeyDetailBack;//红包详情返回
    public String monkeyDetailAuthBack;//红包详情认证返回
    public String monkeyDetailNickName;//红包详情用户昵称
    public String monkeyDetailMoney;//红包详情抢到金额

    public ConfigBean() {
    }

    protected ConfigBean(Parcel in) {
        homeListViewId = in.readString();
        unreadId = in.readString();
        descId = in.readString();
        chatContainerId = in.readString();
        chatContainerBackId = in.readString();
        chatContainerTitleId = in.readString();
        chatListViewId = in.readString();
        chatItemId = in.readString();
        chatAvatarId = in.readString();
        monkeyId = in.readString();
        monkeyCanOpenFlagId = in.readString();
        monkeyOpenAction = in.readString();
        monkeyBack = in.readString();
        monkeyDetailBack = in.readString();
        monkeyDetailAuthBack = in.readString();
        monkeyDetailNickName = in.readString();
        monkeyDetailMoney = in.readString();
    }

    public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
        @Override
        public ConfigBean createFromParcel(Parcel in) {
            return new ConfigBean(in);
        }

        @Override
        public ConfigBean[] newArray(int size) {
            return new ConfigBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(homeListViewId);
        dest.writeString(unreadId);
        dest.writeString(descId);
        dest.writeString(chatContainerId);
        dest.writeString(chatContainerBackId);
        dest.writeString(chatContainerTitleId);
        dest.writeString(chatListViewId);
        dest.writeString(chatItemId);
        dest.writeString(chatAvatarId);
        dest.writeString(monkeyId);
        dest.writeString(monkeyCanOpenFlagId);
        dest.writeString(monkeyOpenAction);
        dest.writeString(monkeyBack);
        dest.writeString(monkeyDetailBack);
        dest.writeString(monkeyDetailAuthBack);
        dest.writeString(monkeyDetailNickName);
        dest.writeString(monkeyDetailMoney);
    }

    public String getHomeListViewId() {
        return homeListViewId;
    }

    public String getUnreadId() {
        return unreadId;
    }

    public String getDescId() {
        return descId;
    }

    public String getChatContainerId() {
        return chatContainerId;
    }

    public String getChatContainerBackId() {
        return chatContainerBackId;
    }

    public String getChatContainerTitleId() {
        return chatContainerTitleId;
    }

    public String getChatListViewId() {
        return chatListViewId;
    }

    public String getChatItemId() {
        return chatItemId;
    }

    public String getChatAvatarId() {
        return chatAvatarId;
    }

    public String getMonkeyId() {
        return monkeyId;
    }

    public String getMonkeyCanOpenFlagId() {
        return monkeyCanOpenFlagId;
    }

    public String getMonkeyOpenAction() {
        return monkeyOpenAction;
    }

    public String getMonkeyBack() {
        return monkeyBack;
    }

    public String getMonkeyDetailBack() {
        return monkeyDetailBack;
    }

    public String getMonkeyDetailAuthBack() {
        return monkeyDetailAuthBack;
    }

    public String getMonkeyDetailNickName() {
        return monkeyDetailNickName;
    }

    public String getMonkeyDetailMoney() {
        return monkeyDetailMoney;
    }
}
