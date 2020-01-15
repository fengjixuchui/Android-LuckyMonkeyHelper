package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.base.Constants;
import com.newtoncode.app.luckymonkeyhelper.base.MainApplication;
import com.newtoncode.app.luckymonkeyhelper.config.AppConfig;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.lib.ads.model.AdConfigModel;
import com.newtoncode.lib.ads.views.AdView;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout st_red_model;
    private RelativeLayout st_update_config;
    private RelativeLayout st_usual_method;
    private RelativeLayout st_score;
    private RelativeLayout st_feedback;
    private RelativeLayout st_about;
    private Switch switch_rush_mode;

    private AdView adView;

    @Override
    public Class getTag(Class clazz) {
        return MainActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.setting).homeAsUp();


        this.st_red_model = (RelativeLayout) this.findViewById(R.id.st_red_model);
        this.st_update_config = (RelativeLayout) this.findViewById(R.id.st_update_config);
        this.st_usual_method = (RelativeLayout) this.findViewById(R.id.st_usual_method);
        this.st_score = (RelativeLayout) this.findViewById(R.id.st_score);
        this.st_feedback = (RelativeLayout) this.findViewById(R.id.st_feedback);
        this.st_about = (RelativeLayout) this.findViewById(R.id.st_about);
        this.switch_rush_mode = (Switch) this.findViewById(R.id.switch_rush_mode);
        adView = (AdView) findViewById(R.id.ad_view);

        this.bindEvent();
    }

    private void bindEvent() {
        this.st_red_model.setOnClickListener(this);
        this.st_update_config.setOnClickListener(this);
        this.st_usual_method.setOnClickListener(this);
        this.st_score.setOnClickListener(this);
        this.st_feedback.setOnClickListener(this);
        this.st_about.setOnClickListener(this);
        this.switch_rush_mode.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Log.e(TAG, "=====AppConfig.isAlwaysStayHome(this) ==" + AppConfig.isAlwaysStayHome(this));

        switch_rush_mode.setChecked(AppConfig.isAlwaysStayHome(this));
        switch_rush_mode.setText(AppConfig.isAlwaysStayHome(this) ? R.string.rush_mode_auto : R.string.rush_mode_chat);

        //初始化反馈信息
        FeedbackAPI.init(MainApplication.getInstance(), Constants.ALI_FEEDBACK_APP_KEY);


        if (adView != null) {
            adView.setCallback(new AdView.Callback() {
                @Override
                public void onLoad(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onLoad=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("设置广告", "成功", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onError=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("设置广告", "失败", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClicked(AdConfigModel configModel) {
                    AnalysisUtil.logEvent("设置广告", "点击", configModel.getPlacementId());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_rush_mode:
                Log.e(TAG, "=====AppConfig.isAlwaysStayHome(this) ==" + AppConfig.isAlwaysStayHome(this));
                AppConfig.setAlwaysStayHome(this, switch_rush_mode.isChecked());
                switch_rush_mode.setText(AppConfig.isAlwaysStayHome(this) ? R.string.rush_mode_auto : R.string.rush_mode_chat);
                AnalysisUtil.logEvent("抢红包姿势切换次数");
                break;
//            case R.id.st_red_model:
//                startAwesomeActivity(new Intent(this, RushModeActivity.class));
//                break;
            case R.id.st_update_config:

                break;
            case R.id.st_usual_method:
                AnalysisUtil.logEvent("打开使用攻略");
                startAwesomeActivity(new Intent(this, AboutUsageActivity.class));
                break;
            case R.id.st_score:
                AnalysisUtil.logEvent("打开去评分");
                AppUtil.openMarketDetail(this, this.getPackageName());
                break;
            case R.id.st_feedback:
//                startAwesomeActivity(new Intent(this, FeedBackActivity.class));
                AnalysisUtil.logEvent("打开反馈");
                FeedbackAPI.openFeedbackActivity();
                break;
            case R.id.st_about:
                AnalysisUtil.logEvent("打开关于");
                startAwesomeActivity(new Intent(this, AboutUsActivity.class));
                break;

            default:
                break;
        }
    }
}
