package com.newtoncode.app.luckymonkeyhelper.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.AppSettings;
import com.newtoncode.app.luckymonkeyhelper.ui.views.GuideCardView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by jeanboy on 2017/1/19.
 */

public class AccessTipsDialog extends Dialog implements View.OnClickListener {

    private ViewPager view_pager;
    private MyViewPagerAdapter pagerAdapter;
    private List<GuideCardView> cardViewList = new ArrayList<>();

    private CircleIndicator view_indicator;
    private Button btn_next;

    public AccessTipsDialog(Context context) {
        super(context, R.style.DialogTheme_Alert);
        setContentView(R.layout.dialog_access_tips);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_indicator = (CircleIndicator) findViewById(R.id.view_indicator);
        btn_next = (Button) findViewById(R.id.btn_next);

        for (int i = 0; i < 3; i++) {
            GuideCardView cardView = new GuideCardView(context);
            cardView.setStep(i);
            cardViewList.add(cardView);
        }
        pagerAdapter = new MyViewPagerAdapter();
        view_pager.setAdapter(pagerAdapter);
        view_indicator.setViewPager(view_pager);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(AccessTipsDialog.class.getName(), "======position========" + position);
                if (btn_next != null) {
                    btn_next.setText(position >= (cardViewList.size() - 1) ? R.string.guide_ready : R.string.guide_next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_next.setOnClickListener(this);

        if (AppSettings.getServiceOpenCount(getContext()) > 1) {
            view_pager.setCurrentItem(cardViewList.size() - 1);
        }
    }

    @Deprecated
    @Override
    public void show() {
    }

    public void toShow(Callback callback) {
        super.show();
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (view_pager.getCurrentItem() < cardViewList.size() - 1) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                } else {
                    dismiss();
                    if (callback != null) {
                        callback.onOpen();
                    }
                }
                break;
        }
    }

    private class MyViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return cardViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GuideCardView cardView = cardViewList.get(position);
            container.addView(cardView);
            return cardView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(cardViewList.get(position));
        }

    }

    private Callback callback;

    public interface Callback {
        void onOpen();
    }
}
