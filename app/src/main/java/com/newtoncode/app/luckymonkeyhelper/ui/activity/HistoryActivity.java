package com.newtoncode.app.luckymonkeyhelper.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.base.BaseActivity;
import com.newtoncode.app.luckymonkeyhelper.base.MainApplication;
import com.newtoncode.app.luckymonkeyhelper.dao.LuckyMonkeyModelDao;
import com.newtoncode.app.luckymonkeyhelper.manager.DBManager;
import com.newtoncode.app.luckymonkeyhelper.model.LuckyMonkeyModel;
import com.newtoncode.app.luckymonkeyhelper.ui.adapter.RedHistoryAdapter;
import com.newtoncode.app.luckymonkeyhelper.utils.AnalysisUtil;
import com.newtoncode.lib.ads.model.AdConfigModel;
import com.newtoncode.lib.ads.views.AdView;
import com.newtoncode.recyclerviewhelper.RecyclerViewHelper;
import com.newtoncode.recyclerviewhelper.listener.HeaderConvertListener;
import com.newtoncode.recyclerviewhelper.listener.LoadMoreListener;
import com.newtoncode.recyclerviewhelper.listener.TipsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author: song
 * time: 2017/1/6 14:34
 * describe:红包历史记录
 */
public class HistoryActivity extends BaseActivity {

    private RecyclerView recycle_view;
    private TextView totalMoneyTv;
    private TextView redCountTv;
    private TextView maxMoneyTv;

    private AdView adView;

    @Override
    public Class getTag(Class clazz) {
        return HistoryActivity.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    private RecyclerViewHelper recyclerViewHelper;
    private List<LuckyMonkeyModel> luckyMonkeyModelList;

    @Override
    public void setupView(Bundle savedInstanceState) {
        setToolsBarTitle(R.string.monkey_history);
        homeAsUp();
        initView();

        luckyMonkeyModelList = new ArrayList<>();
        RedHistoryAdapter adapter = new RedHistoryAdapter(luckyMonkeyModelList);
        recyclerViewHelper = new RecyclerViewHelper(recycle_view, adapter);
        //设置没有数据的Tips
        recyclerViewHelper.setTipsEmptyView(R.layout.view_data_empty);
        //设置加载中的Tips
        recyclerViewHelper.setTipsLoadingView(R.layout.view_data_loading);
        //设置加载失败的Tips
        recyclerViewHelper.setTipsErrorView(R.layout.view_data_error);
        MainApplication.getInstance().refreshHistory();
        if (MainApplication.getInstance().getTotalCount() > 0) {
            //设置header
            recyclerViewHelper.setHeaderView(R.layout.view_history_header);
            recyclerViewHelper.setHeaderConvertListener(new HeaderConvertListener() {
                @Override
                public void convert(RecyclerView.ViewHolder holder, int position) {

                    totalMoneyTv = (TextView) holder.itemView.findViewById(R.id.totalMoney);
                    redCountTv = (TextView) holder.itemView.findViewById(R.id.redCount);
                    maxMoneyTv = (TextView) holder.itemView.findViewById(R.id.maxMoney);
                    redCountTv.setText(MainApplication.getInstance().getTotalCount() + "");
                    totalMoneyTv.setText(MainApplication.getInstance().getTotalMoneyString());
                    LuckyMonkeyModel luckyMonkeyModel = DBManager.getInstance().queryBuilder(LuckyMonkeyModel.class).orderDesc(LuckyMonkeyModelDao.Properties.Money).limit(1).unique();
                    if (luckyMonkeyModel != null) {
                        maxMoneyTv.setText(luckyMonkeyModel.getMoney() / 100f + "");
                    } else {
                        maxMoneyTv.setText("0");
                    }
                }
            });
        }
        //加载失败，没有数据时Tips的接口
        recyclerViewHelper.setTipsListener(new TipsListener() {
            @Override
            public void retry() {
                loadHistory(System.currentTimeMillis(), 0);
            }
        });
        //加载更多的接口
        recyclerViewHelper.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                loadHistory(luckyMonkeyModelList.get(luckyMonkeyModelList.size() - 1).getCreateTime(), 1);
            }
        });

    }

    private void initView() {
        this.recycle_view = (RecyclerView) this.findViewById(R.id.recycle_view);
        adView = (AdView) findViewById(R.id.ad_view);
    }

    @Override
    public void initData() {
        loadHistory(System.currentTimeMillis(), 0);

        if (adView != null) {
            adView.setCallback(new AdView.Callback() {
                @Override
                public void onLoad(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onLoad=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("历史记录广告", "成功", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(AdConfigModel configModel) {
                    try {
                        Log.e(TAG, "======onError=====" + JSON.toJSONString(configModel));
                        AnalysisUtil.logEvent("历史记录广告", "失败", configModel.getPlacementId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClicked(AdConfigModel configModel) {
                    AnalysisUtil.logEvent("历史记录广告", "点击", configModel.getPlacementId());
                }
            });
        }
    }

    private void loadHistory(Long time, int source) {
        List<LuckyMonkeyModel> _luckyMonkeyModelList = DBManager.getInstance().queryBuilder(LuckyMonkeyModel.class).
                where(LuckyMonkeyModelDao.Properties.CreateTime.lt(time))
                .orderDesc(LuckyMonkeyModelDao.Properties.CreateTime).limit(20).list();
        if (source == 0) {
            luckyMonkeyModelList.clear();
        }
        luckyMonkeyModelList.addAll(_luckyMonkeyModelList);
        if (source == 0) {
            recyclerViewHelper.loadTipsComplete();
        }
        if (_luckyMonkeyModelList.size() < 20) {
            recyclerViewHelper.loadMoreFinish(false);
        } else {
            recyclerViewHelper.loadMoreFinish(true);
        }
//        Log.e("xxx", luckyMonkeyModelList.size() + "   size");
    }
}
