package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.tencent.bugly.beta.Beta;

public class AboutUsActivity extends BaseActivity {


    private TextView tv_version_name;

    @Override
    public Class getTag(Class clazz) {
        return AboutUsActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.title_about_us).homeAsUp();
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        tv_version_name.setText(getResources().getString(R.string.app_version_name, AppUtil.getVersionName(this)));
    }

    @Override
    public void initData() {

    }

    public void serviceRule(View v) {
        AnalysisUtil.logEvent("打开使用声明");
        startAwesomeActivity(new Intent(AboutUsActivity.this, UseStatementActivity.class));
    }

    public void checkUpdate(View v) {
        //点击手动检测是否可以升级
//        参数1：isManual 用户手动点击检查，非用户点击操作请传false
//        参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]

        AnalysisUtil.logEvent("检查更新");
        Beta.checkUpgrade(true, false);
    }
}
