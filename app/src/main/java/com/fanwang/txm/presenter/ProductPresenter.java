package com.fanwang.txm.presenter;

import android.app.Activity;
import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.bean.DataBean;
import com.fanwang.txm.utils.TopRightMenuDataTool;
import com.fanwang.txm.view.impl.ProductContract;
import com.flyco.roundview.RoundTextView;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zaaach.toprightmenu.TopRightMenuTool;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class ProductPresenter extends ProductContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> listBean = new ArrayList<>();
                for (int i = 0;i < 15;i++){
                    DataBean bean = new DataBean();
                    bean.setType(1);
                    listBean.add(bean);
                }
                mView.setData(listBean);
            }
        }, 2000);

    }

    private String startYear, endYear;
    private String startMonth, endMonth;
    private String startDay, endDay;

    @Override
    public void getYear(final RoundTextView year, final RoundTextView month, final RoundTextView day) {
        final List<MenuItem> listYear = TopRightMenuDataTool.getYearList();
        TopRightMenuTool.TopRightMenu((Activity) act,listYear , year, new TopRightMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                MenuItem item = listYear.get(position);
                year.setText(item.getText() + "年");
                month.setText("");
                day.setText("");
                startMonth = "";
                startDay = "";
                endMonth = "";
                endDay = "";
                startYear = item.getText();
                endYear = item.getText();
            }
        });
    }

    @Override
    public void getMonth(RoundTextView year, final RoundTextView month, final RoundTextView day) {
        String startYear = year.getText().toString();
        if (!StringUtils.isEmpty(startYear)){
            final List<MenuItem> listMonth = TopRightMenuDataTool.getMonthList(startYear.substring(0, startYear.length() - 1));
            TopRightMenuTool.TopRightMenu((Activity) act,listMonth , month, new TopRightMenu.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position) {
                    MenuItem item = listMonth.get(position);
                    month.setText(item.getText() + "月");
                    day.setText("");
                    startDay = "";
                    endDay = "";
                    startMonth = item.getText();
                    endMonth = item.getText();
                }
            });
        }
    }

    @Override
    public void getDay(RoundTextView year, RoundTextView month, final RoundTextView day) {
        String startYear = year.getText().toString();
        String startMonth = month.getText().toString();
//        String startDay = year.getText().toString();
        if (!StringUtils.isEmpty(startMonth)){
            final List<MenuItem> listDay = TopRightMenuDataTool.getDayList(startYear.substring(0, startYear.length() - 1), startMonth.substring(0, startMonth.length() - 1));
            TopRightMenuTool.TopRightMenu((Activity) act, listDay , day, new TopRightMenu.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position) {
                    MenuItem item = listDay.get(position);
                    day.setText(item.getText() + "日");
                    startDay = item.getText();
                }
            });
        }
    }

    @Override
    public void search() {
        if (StringUtils.isEmpty(startYear) || StringUtils.isEmpty(startMonth) || StringUtils.isEmpty(startDay) || StringUtils.isEmpty(endYear) || StringUtils.isEmpty(endMonth) || StringUtils.isEmpty(endDay)){
            ToastUtils.showShort(act.getString(R.string.please_select_time_period));
            return;
        }
        if (startMonth.length() == 1){
            startMonth = "0" + startMonth;
        }
        if (endMonth.length() == 1){
            endMonth = "0" + endMonth;
        }



    }

}
