package com.newtoncode.app.luckymonkeyhelper.base;

import android.app.Application;

import com.crashlytics.android.answers.Answers;
import com.newtoncode.app.luckymonkeyhelper.manager.DBManager;
import com.newtoncode.app.luckymonkeyhelper.model.LuckyMonkeyModel;
import com.newtoncode.app.luckymonkeyhelper.utils.AppUtil;
import com.newtoncode.app.luckymonkeyhelper.utils.CrashReportUtil;
import com.newtoncode.lib.ads.AdManager;
import com.tendcloud.tenddata.TCAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * author: 乔晓松
 * time: 2016/12/6 16:31
 * describe:
 */
public class MainApplication extends Application {

    private List<LuckyMonkeyModel> luckyMonkeyModelList = new ArrayList<>();

    private static MainApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        DBManager.getInstance().build(getApplicationContext());

        AdManager.getInstance().build(getApplicationContext());
//        AdManager.getInstance().setDebug(BuildConfig.DEBUG);

        //初始化bugly
        CrashReportUtil.initCrashReport(getApplicationContext());

        try {

            TCAgent.init(this.getApplicationContext(), AppUtil.getAppMetaData(getApplicationContext(),"TD_APP_ID"),
                    AppUtil.getAppMetaData(getApplicationContext(),"TD_CHANNEL_ID"));
            TCAgent.setReportUncaughtExceptions(false);

            Fabric.with(this, new Answers());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MainApplication getInstance() {
        return INSTANCE;
    }

    public void refreshHistory() {
        List<LuckyMonkeyModel> luckyMonkeyHistory = DBManager.getInstance().getAll(LuckyMonkeyModel.class);
        if (luckyMonkeyHistory != null) {
            luckyMonkeyModelList.clear();
            luckyMonkeyModelList.addAll(luckyMonkeyHistory);
        }
    }

    public int getTotalCount() {
        return luckyMonkeyModelList.size();
    }

    public int getTotalMoney() {
        int totalMoney = 0;
        for (LuckyMonkeyModel monkeyModel : luckyMonkeyModelList) {
            totalMoney += monkeyModel.getMoney();
        }
        return totalMoney;
    }

    public String getTotalMoneyString() {
        DecimalFormat df = new DecimalFormat("0.00");
        double money = getTotalMoney() / 100.00D;
        return df.format(money);
    }
}
