package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;

import java.lang.reflect.Field;

/**
 * author: 乔晓松
 * email: qiaoxiaosong@fotoable.com
 * time: 2016/12/27 13:45
 * describe:使用声明
 */
public class UseStatementActivity extends BaseActivity {

    private WebView webView;

    @Override
    public Class getTag(Class clazz) {
        return UseStatementActivity.this.getClass();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_use_statement;
    }

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.use_statement);
        homeAsUp();
        initView();
    }

    private void initView() {
        this.webView = (WebView) this.findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(false);// 禁止缩放
    }

    @Override
    public void initData() {
        webView.loadUrl("file:///android_asset/newtoncode_experience_plan_content_and_privacy.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.webView != null) {
            this.webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.webView != null) {
            this.webView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            setConfigCallback(null);
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }
            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e) {
        }
    }
}
