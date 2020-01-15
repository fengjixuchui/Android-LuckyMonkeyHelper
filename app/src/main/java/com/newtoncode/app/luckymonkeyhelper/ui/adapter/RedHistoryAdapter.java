package com.newtoncode.app.luckymonkeyhelper.ui.adapter;

import android.support.annotation.NonNull;

import com.newtoncode.app.luckymonkeyhelper.R;
import com.newtoncode.app.luckymonkeyhelper.model.LuckyMonkeyModel;
import com.newtoncode.recyclerviewhelper.adapter.BaseViewHolder;
import com.newtoncode.recyclerviewhelper.adapter.CommonAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * author: song
 * time: 2017/1/6 16:30
 * describe:红包历史记录适配器
 */
public class RedHistoryAdapter extends CommonAdapter<LuckyMonkeyModel> {

    private SimpleDateFormat simpleDateFormat;

    public RedHistoryAdapter(@NonNull List<LuckyMonkeyModel> dataList) {
        super(dataList, R.layout.item_history);
        simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    }

    @Override
    public void convert(BaseViewHolder holder, LuckyMonkeyModel luckyMonkeyModel, int position) {
        holder.setText(R.id.nickName, luckyMonkeyModel.getUserNick());
        holder.setText(R.id.money, String.valueOf(luckyMonkeyModel.getMoney() / 100f));
        holder.setText(R.id.time, simpleDateFormat.format(luckyMonkeyModel.getCreateTime()));
    }
}
