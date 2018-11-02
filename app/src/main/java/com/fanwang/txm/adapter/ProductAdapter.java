package com.fanwang.txm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseRecyclerviewAdapter;
import com.fanwang.txm.bean.DataBean;

import java.util.List;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class ProductAdapter extends BaseRecyclerviewAdapter<DataBean>{

    public ProductAdapter(Context act, List listBean) {
        super(act, listBean);
    }

    private final int TITLE_BLUE_TYPE = 0;
    private final int CONTENT_TYPE = 1;

    @Override
    public int getItemViewType(int position) {
        if (listBean.get(position).getType() == 0){
            return TITLE_BLUE_TYPE;
        }else {
            return CONTENT_TYPE;
        }
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        if (bean.getType() == TITLE_BLUE_TYPE){
            setTextView(viewHolder.tvName, "日期");
            setTextView(viewHolder.tvName2, "型号");
            setTextView(viewHolder.tvName3, "名称");
            setTextView(viewHolder.tvName4, "条码");
            setTextView(viewHolder.tvName5, "结果");
        }else {
            viewHolder.tvName.setText(position + "");
            viewHolder.tvName2.setText("201");
            viewHolder.tvName3.setText("2514605602");
            viewHolder.tvName4.setText("2514605602");
            viewHolder.tvName5.setText("结果");
        }
    }

    private void setTextView(TextView textView, String text){
        textView.setText(text);
        textView.setBackgroundResource(R.color.blue_26A4E1);
        textView.setTextColor(act.getColor(R.color.white));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_montior, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvName2;
        TextView tvName3;
        TextView tvName4;
        TextView tvName5;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvName2 = itemView.findViewById(R.id.tv_name2);
            tvName3 = itemView.findViewById(R.id.tv_name3);
            tvName4 = itemView.findViewById(R.id.tv_name4);
            tvName5 = itemView.findViewById(R.id.tv_name5);
        }
    }

}
