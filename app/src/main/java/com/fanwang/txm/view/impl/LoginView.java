package com.fanwang.txm.view.impl;

import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseView;

/**
 * 作者：yc on 2018/8/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public interface LoginView {

    abstract class Presenter extends BasePresenter<View> {
        public abstract void userVerLogin(String phoneNum,String password);
    }

    interface View extends IBaseView {
        void loginSuccess();
    }

}
