package com.fanwang.txm.utils;

import com.blankj.utilcode.util.TimeUtils;
import com.zaaach.toprightmenu.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class TopRightMenuDataTool {

    /**
     *  获取年
     * @return
     */
    public static List<MenuItem> getYearList(){
        int years = Integer.valueOf(getYear());
        final List<MenuItem> menuItems = new ArrayList<>();
        for (int i = years;i > years - 6;i--){
            MenuItem item = new MenuItem();
            item.setText(i + "");
            menuItems.add(item);
        }
        return menuItems;
    }
     /**
     *  获取月
     * @return
     */
    public static List<MenuItem> getMonthList(String year){
        final List<MenuItem> menuItems = new ArrayList<>();
        if (year.equals(getYear())){//是否今年
            for (int i = 1;i < Integer.valueOf(getMonth()) + 1;i++){
                MenuItem item = new MenuItem();
                item.setText(i + "");
                menuItems.add(item);
            }
        }else {
            for (int i = 1;i < 13;i++){
                MenuItem item = new MenuItem();
                item.setText(i + "");
                menuItems.add(item);
            }
        }
        return menuItems;
    }
    /**
     *  获取日
     * @return
     */
    public static List<MenuItem> getDayList(String year, String month){
        final List<MenuItem> menuItems = new ArrayList<>();
        int month1 = Integer.valueOf(getMonth());
        if (year.equals(getYear()) && Integer.valueOf(month) == month1){//是否当年  当月
            for (int i = 1;i < Integer.valueOf(getDay()) + 1;i++){
                MenuItem item = new MenuItem();
                item.setText(i + "");
                menuItems.add(item);
            }
        }else {
            for (int i = 1;i < getDaysByYearMonth(Integer.valueOf(year), Integer.valueOf(month)) + 1;i++){
                MenuItem item = new MenuItem();
                item.setText(i + "");
                menuItems.add(item);
            }
        }
        return menuItems;
    }


    //获取当年
    private static String getYear(){
        return TimeUtils.getNowString(new SimpleDateFormat("yyyy"));
    }

    //获取当月
    private static String getMonth(){
        return TimeUtils.getNowString(new SimpleDateFormat("MM"));
    }

    //获取当天
    private static String getDay(){
        return TimeUtils.getNowString(new SimpleDateFormat("dd"));
    }


    /**
     * 根据 年、月 获取对应的月份 的 天数
     */
    private static int getDaysByYearMonth(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


}
