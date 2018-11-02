package com.fanwang.txm.view.impl;

import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseListView;
import com.fanwang.txm.base.IBaseView;
import com.fanwang.txm.bean.DataBean;

import java.util.List;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public interface MonitorContarct {

    interface View extends IBaseListView {
        void setData2(List<DataBean> listBean);
    }

    abstract class Presenter extends BasePresenter<View>{

        public abstract void onRequest(int pagerNumber);
        public abstract void onRequest2(int pagerNumber);
    }


}
