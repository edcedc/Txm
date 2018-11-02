package com.fanwang.txm.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.fanwang.txm.R;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MonitorHeadView extends WithScrollGridView{

    public MonitorHeadView(Context context) {
        super(context);
        init(context);
    }

    public MonitorHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MonitorHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private MonitorHeadAdapter adapter;

    private SparseArray<String> sparseArray = new SparseArray<>();


    private Context act;
    private void init(Context act){
        this.act = act;
//        LayoutInflater.from(act).inflate(R.layout.include_monitor_head,this);
//        gridView = findViewById(R.id.gridView);
        setHorizontalSpacing(ConvertUtils.px2dp(2));
        if (adapter == null){
            adapter = new MonitorHeadAdapter();
        }
        setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setData(SparseArray<String> array){
        for (int i = 0;i < array.size();i++){
            String s = array.get(i);
            sparseArray.put(i, s);
        }
        setNumColumns(sparseArray.size());
        adapter.notifyDataSetChanged();
    }

    class MonitorHeadAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sparseArray.size();
        }

        @Override
        public Object getItem(int i) {
            return sparseArray.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(act, R.layout.i_monitor_head, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(sparseArray.get(i));
            return convertView;
        }
    }

    class ViewHolder{

        TextView tvName;

        public ViewHolder(View convertView) {
            tvName = convertView.findViewById(R.id.tv_name);
        }
    }

}
