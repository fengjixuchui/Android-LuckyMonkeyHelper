package com.newtoncode.app.luckymonkeyhelper.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.newtoncode.app.luckymonkeyhelper.R;


/**
 * Created by Next on 2016/7/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public String TAG;

    private Toolbar toolbar;

    private boolean isDestroy = false;

    public BaseActivity() {
        TAG = this.getClass().getSimpleName();
    }

    public abstract Class getTag(Class clazz);

    public abstract int getLayoutId();

    public abstract void setupView(Bundle savedInstanceState);

    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isDestroy = false;
        setContentView(getLayoutId());
        TAG = getTag(BaseActivity.class).getSimpleName();

        setupToolbar();
        setupView(savedInstanceState);
        initData();
    }

    /**
     * 初始化Toolbar
     */
    private void setupToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle("");
                setSupportActionBar(toolbar);
            }
        }
    }

    public BaseActivity addMenu(int layout, Toolbar.OnMenuItemClickListener itemClickListener) {
        if (toolbar != null) {
            toolbar.inflateMenu(layout);
            toolbar.setOnMenuItemClickListener(itemClickListener);
        }
        return this;
    }

    /**
     * 设置Toolbar的标题
     *
     * @param title
     * @return
     */
    public BaseActivity setToolsBarTitle(String title) {
        if (toolbar != null) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            if (titleView != null) {
                titleView.setText(title);
            }
        }
        return this;
    }

    public BaseActivity setToolsBarTitle(int titleId) {
        if (toolbar != null && titleId > 0) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            if (titleView != null) {
                titleView.setText(titleId);
            }
        }
        return this;
    }

    public void setToolbarNavigation(int resId, int descId, View.OnClickListener listener) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(resId);
            toolbar.setNavigationContentDescription(descId);
            toolbar.setNavigationOnClickListener(listener);
        }
    }

    /**
     * 设置Toolbar左上角箭头可用
     *
     * @return
     */
    public BaseActivity homeAsUp() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return this;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
        super.onBackPressed();
    }

    public boolean isDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isDestroyed();
        } else {
            return isDestroy;
        }
    }

    @Override
    protected void onDestroy() {
        this.isDestroy = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
        super.onDestroy();
    }

    /**
     * startActivity兼容处理
     *
     * @param intent
     * @param pairs
     */
    @SafeVarargs
    public final void startAwesomeActivity(Intent intent, Pair<View, String>... pairs) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs).toBundle());
            } else {
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
