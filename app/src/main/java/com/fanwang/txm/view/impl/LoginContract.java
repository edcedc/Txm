package com.fanwang.txm.view.impl;


import com.fanwang.txm.base.BaseModel;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseView;
import com.fanwang.txm.bean.BaseResponseBean;
import com.fanwang.txm.bean.DataBean;

import io.reactivex.Observable;

/**
 * Created by Simple on 2018/7/13.
 */

public interface LoginContract {

    interface Model extends BaseModel {
        Observable<BaseResponseBean<DataBean>> userVerLogin(String phoneNum, String password, String mVerCode);
    }

    interface View extends IBaseView {
        void loginSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String phone, String pwd);
    }
}
