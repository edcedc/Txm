package com.fanwang.txm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwang.txm.R;
import com.fanwang.txm.bean.DataBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MontiorViewHolder extends BaseViewHolder<DataBean>{

    TextView tvName;

    public MontiorViewHolder(ViewGroup parent) {
        super(parent, R.layout.i_montior);
        tvName = $(R.id.tv_name);
    }

    @Override
    public void setData(DataBean data) {
        super.setData(data);
        tvName.setText(getDataPosition() + "");
    }
}
