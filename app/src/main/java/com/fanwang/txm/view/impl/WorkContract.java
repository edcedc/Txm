package com.fanwang.txm.view.impl;

import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseListView;
import com.fanwang.txm.base.IBaseView;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public interface WorkContract {

    interface View extends IBaseListView{

    }

    abstract class Presenter extends BasePresenter<View>{
        public abstract void onRequest(int pagerNumber);
    }

}
