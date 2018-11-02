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
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MontiorAdater extends BaseRecyclerviewAdapter<DataBean>{

    public MontiorAdater(Context act, List listBean) {
        super(act, listBean);
    }

    private final int MODE_TITLT = 0;
    private final int WORK_TITLE = 1;

    @Override
    public int getItemViewType(int position) {
        int type = listBean.get(position).getType();
        if (type == 0){
            return MODE_TITLT;
        }else if (type == 1){
            return WORK_TITLE;
        }else {
            return type;
        }
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        if (bean.getType() == MODE_TITLT){
            setTextView(viewHolder.tvName, "机型");
            setTextView(viewHolder.tvName2, "产量");
            setTextView(viewHolder.tvName3, "完成率");
            setTextView(viewHolder.tvName4, "合格率");
            setTextView(viewHolder.tvName5, "状态");
        }else if (bean.getType() == WORK_TITLE){
            setTextView(viewHolder.tvName, "工位");
            setTextView(viewHolder.tvName2, "产量");
            setTextView(viewHolder.tvName3, "完成率");
            setTextView(viewHolder.tvName4, "合格率");
            setTextView(viewHolder.tvName5, "状态");
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
