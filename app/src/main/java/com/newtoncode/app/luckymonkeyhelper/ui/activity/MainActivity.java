package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.AppSettings;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.base.MainApplication;
import com.newtoncode.app.luckymonkeyhelper.config.WxConfig;
import com.newtoncode.app.luckymonkeyhelper.manager.WxConfigManager;
import com.newtoncode.app.luckymonkeyhelper.ui.dialog.AccessTipsDialog;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.ServiceUtil;
import com.newtoncode.lib.ads.model.AdConfigModel;
import com.newtoncode.lib.ads.views.AdView;


public class MainActivity extends BaseActivity {


    private TextView tv_count, tv_money_total;
    private Button btn_open_service;

    private AdView adView;

    @Override
    public Class getTag(Class clazz) {
        return MainActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
//        setToolsBarTitle(R.string.app_name_title)
//                .setToolbarNavigation(R.drawable.ic_history_white_24dp, R.string.action_history,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                toOpenHistory();
//                            }
//                        });

        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_money_total = (TextView) findViewById(R.id.tv_money_total);
        btn_open_service = (Button) findViewById(R.id.btn_open_service);

        adView = (AdView) findViewById(R.id.ad_view);

        AnalysisUtil.logEvent("进入主界面");
    }

    @Override
    public void initData() {
        WxConfigManager.getInstance().loadLocalConfigData();
        WxConfigManager.getInstance().loadUmengOnLineParams();
        checkWxConfigInfo();
    }

    /**
     * 打开红包记录
     */
    public void toOpenHistory(View v) {
        AnalysisUtil.logEvent("红包记录界面展示次数");
        startAwesomeActivity(new Intent(this, HistoryActivity.class));
    }

    public void toOpenSettings(View v) {
        AnalysisUtil.logEvent("进入设置界面");
        startAwesomeActivity(new Intent(this, SettingsActivity.class));
    }

    public void toOpen(View v) {
        AnalysisUtil.logEvent("打开辅助界面次数");
//        ToastUtil.toast(this, R.string.service_tips);
        AccessTipsDialog accessTipsDialog = new AccessTipsDialog(this);
        accessTipsDialog.toShow(new AccessTipsDialog.Callback() {
            @Override
            public void onOpen() {
                AppSettings.setServiceOpenOnce(MainActivity.this);
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                MainActivity.this.startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_settings, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                startAwesomeActivity(new Intent(this, SettingsActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshViewData();
    }

    private void refreshViewData() {
        MainApplication.getInstance().refreshHistory();
        tv_count.setText(String.valueOf(MainApplication.getInstance().getTotalCount()));
        tv_money_total.setText(getResources().getString(R.string.helper_monkey_total_money,
                MainApplication.getInstance().getTotalMoneyString()));

        if (ServiceUtil.isAccessibilitySettingsOn(this)) {
            btn_open_service.setEnabled(false);
            btn_open_service.setClickable(false);
            btn_open_service.setText(R.string.main_service_opened);
        } else {
            btn_open_service.setEnabled(true);
            btn_open_service.setClickable(true);
            btn_open_service.setText(R.string.main_open_service);
        }

        if (adView != null) {
            adView.setCallback(new AdView.Callback() {
                @Override
                public void onLoad(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onLoad=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("主界面广告", "成功", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onError=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("主界面广告", "失败", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClicked(AdConfigModel configModel) {
                    AnalysisUtil.logEvent("主界面广告", "点击", configModel.getPlacementId());
                }
            });

        }
    }

    /**
     * 判断是否有新的微信配置信息
     */
    private void checkWxConfigInfo() {
        if (!AppUtil.isInstalled(MainApplication.getInstance(), WxConfig.appPackageName)) {//是否安装了微信
            Toast.makeText(MainActivity.this, R.string.please_install_wx, Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(AppUtil.getVersion(MainApplication.getInstance(), WxConfig.appPackageName))) {//是否可以正常获取到微信的版本号
            Toast.makeText(MainActivity.this, R.string.get_wx_version_error, Toast.LENGTH_SHORT).show();
            return;
        } else if (!WxConfigManager.getInstance().isSupport()) {//是否有此版本的配置
            Toast.makeText(MainActivity.this, R.string.dont_support_wx_version, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
