package com.newtoncode.app.luckymonkeyhelper.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtoncode.app.luckymonkeyhelper.R;

/**
 * Created by jeanboy on 2017/1/20.
 */

public class GuideCardView extends FrameLayout {

    private ImageView iv_step_bg;
    private TextView tv_step_tips;
    public GuideCardView(Context context) {
        this(context, null);
    }

    public GuideCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_guide_card, this);
        iv_step_bg= (ImageView) findViewById(R.id.iv_step_bg);
        tv_step_tips= (TextView) findViewById(R.id.tv_step_tips);
    }

    public void setStep(int step){
        switch (step){
            case 0:
                iv_step_bg.setImageResource(R.drawable.bg_step_1);
                tv_step_tips.setText(R.string.guide_step1);
                break;
            case 1:
                iv_step_bg.setImageResource(R.drawable.bg_step_2);
                tv_step_tips.setText(R.string.guide_step2);
                break;
            case 2:
                iv_step_bg.setImageResource(R.drawable.bg_step_3);
                tv_step_tips.setText(R.string.guide_step3);
                break;
        }

    }
}
