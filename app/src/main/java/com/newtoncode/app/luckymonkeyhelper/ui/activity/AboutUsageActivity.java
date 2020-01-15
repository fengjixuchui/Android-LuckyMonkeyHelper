package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.lib.ads.model.AdConfigModel;
import com.newtoncode.lib.ads.views.AdView;

public class AboutUsageActivity extends BaseActivity {

    private AdView adView;

    @Override
    public Class getTag(Class clazz) {
        return AboutUsageActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_usage;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.use_strategy).homeAsUp();

        initView();
    }

    private void initView() {
        adView = (AdView) findViewById(R.id.ad_view);
    }

    @Override
    public void initData() {
        if (adView != null) {
            adView.setCallback(new AdView.Callback() {
                @Override
                public void onLoad(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onLoad=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("使用攻略广告", "成功", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onError=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("使用攻略广告", "失败", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClicked(AdConfigModel configModel) {
                    AnalysisUtil.logEvent("使用攻略广告", "点击", configModel.getPlacementId());
                }
            });
        }

    }
}
