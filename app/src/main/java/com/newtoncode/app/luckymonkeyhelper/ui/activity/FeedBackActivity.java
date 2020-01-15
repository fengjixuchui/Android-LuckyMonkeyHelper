package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.base.Constants;
import com.newtoncode.app.luckymonkeyhelper.base.MainApplication;

/**
 * author: 乔晓松
 * email: qiaoxiaosong@fotoable.com
 * time: 2016/12/12 11:30
 * describe:反馈
 */
public class FeedBackActivity extends BaseActivity {

    private FrameLayout container;

    @Override
    public Class getTag(Class clazz) {
        return FeedBackActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.feed_back);
        homeAsUp();
        findViewById(R.id.action_bar).setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        //反馈组件初始化
        FeedbackAPI.init(MainApplication.getInstance(), Constants.ALI_FEEDBACK_APP_KEY);
//        this.container = (FrameLayout) this.findViewById(R.id.container);

        FeedbackAPI.cleanFeedbackFragment();
        Fragment fragment = FeedbackAPI.getFeedbackFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, "FeedBack").commit();

//        FeedbackAPI.openFeedbackActivity();//另起一个Activity打开用户反馈H5界面
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FeedbackAPI.cleanFeedbackFragment();
    }
}
