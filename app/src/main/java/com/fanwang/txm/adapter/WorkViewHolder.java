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

public class WorkViewHolder extends BaseViewHolder<DataBean> {

    TextView tvTitle;
    TextView tvContent;
    TextView tvImport;

    public WorkViewHolder(ViewGroup parent) {
        super(parent, R.layout.i_work);
        tvTitle = $(R.id.tv_title);
        tvContent = $(R.id.tv_content);
        tvImport = $(R.id.tv_import);
    }

    @Override
    public void setData(DataBean data) {
        super.setData(data);
        final int position = getDataPosition();
        tvTitle.setText((position + 1) + "#工位工艺:");
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    private OnClickCameraListener listener;
    public void setOnClickCameraListener(OnClickCameraListener listener){
        this.listener = listener;
    }
    public interface OnClickCameraListener{
        void onClick(int position);
    }

}
