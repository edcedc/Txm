package com.fanwang.txm.view.impl;

import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseListView;
import com.fanwang.txm.base.IBaseView;
import com.flyco.roundview.RoundTextView;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public interface ProductContract {

    interface View extends IBaseListView{

    }

    abstract class Presenter extends BasePresenter<View>{

        public abstract void onRequest(int pagerNumber);

        public abstract void getYear(RoundTextView tvStartYear, RoundTextView tvStartMonth, RoundTextView tvStartDay);

        public abstract void getMonth(RoundTextView tvStartYear, RoundTextView tvStartMonth, RoundTextView tvStartDay);

        public abstract void getDay(RoundTextView tvStartYear, RoundTextView tvStartMonth, RoundTextView tvStartDay);

        public abstract void search();
    }

}
