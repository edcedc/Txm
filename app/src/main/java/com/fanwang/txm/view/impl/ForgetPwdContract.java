package com.fanwang.txm.view.impl;

import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseView;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public interface ForgetPwdContract {

    interface View extends IBaseView{

        void OnCodeSuccess();

        void onSuccess(String phone, String pwd);
    }

    abstract class Presenter extends BasePresenter<View>{

        public abstract void code(String phone);

        public abstract void forget(String phone, String code, String pwd, String pwd1);
    }

}
