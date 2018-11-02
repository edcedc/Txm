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

public class WorkAdapter extends BaseRecyclerviewAdapter {


    public WorkAdapter(Context act, List listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = (DataBean) listBean.get(position);
        viewHolder.tvTitle.setText((position + 1) + "#工位工艺:");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_work, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvContent;
        TextView tvImport;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvImport = itemView.findViewById(R.id.tv_import);
        }
    }

    private WorkViewHolder.OnClickCameraListener listener;
    public void setOnClickCameraListener(WorkViewHolder.OnClickCameraListener listener){
        this.listener = listener;
    }
    public interface OnClickCameraListener{
        void onClick(int position);
    }

}
