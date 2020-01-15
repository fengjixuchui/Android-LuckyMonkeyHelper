package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.lib.ads.model.AdConfigModel;
import com.newtoncode.lib.ads.views.AdView;

public class SplashActivity extends BaseActivity {

    private final static String TAG = SplashActivity.class.getName();

    private TextView tv_version, tv_version_ad;

    private AdView adView;
    private int adErrorCount = 0;

    private static final int WAIT_DELAY_MILLIS = 6000;
    private static final int DELAY_MILLIS = 3000;
    private final Handler mHideHandler = new Handler();
    private final Runnable mJumpRunnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            startAwesomeActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    };


    @Override
    public Class getTag(Class clazz) {
        return SplashActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version_ad = (TextView) findViewById(R.id.tv_version_ad);
        tv_version.setText(getResources().getString(R.string.app_version_name, AppUtil.getVersionName(this)));
        tv_version_ad.setText(getResources().getString(R.string.app_version_name, AppUtil.getVersionName(this)));

        adView = (AdView) findViewById(R.id.ad_view);

        AnalysisUtil.logEvent("应用启动");
    }

    @Override
    public void initData() {
        adErrorCount = 0;

        adView.setCallback(new AdView.Callback() {
            @Override
            public void onLoad(AdConfigModel configModel) {
                try {
                    Log.e(TAG, "======onLoad=====" + JSON.toJSONString(configModel));
                    AnalysisUtil.logEvent("启动屏广告", "成功", configModel.getPlacementId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        delayedHide(DELAY_MILLIS);
                    }
                });
            }

            @Override
            public void onError(AdConfigModel configModel) {
                try {
                    Log.e(TAG, "======onError=====" + JSON.toJSONString(configModel));
                    AnalysisUtil.logEvent("启动屏广告", "失败", configModel.getPlacementId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adErrorCount++;
                if (adErrorCount >= 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            delayedHide(DELAY_MILLIS);
                        }
                    });
                }
            }

            @Override
            public void onClicked(AdConfigModel configModel) {
                AnalysisUtil.logEvent("启动屏广告", "点击", configModel.getPlacementId());
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        delayedHide(WAIT_DELAY_MILLIS);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mJumpRunnable);
        mHideHandler.postDelayed(mJumpRunnable, delayMillis);
    }
}
