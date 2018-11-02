package com.fanwang.txm.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.view.impl.LoginContract;

/**
 * 作者：yc on 2018/8/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String phone, String pwd) {
        if (StringUtils.isEmpty(phone)){
            ToastUtils.showShort(act.getString(R.string.error_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(act.getString(R.string.error_phone1));
            return;
        }
        mView.loginSuccess();
    }
}
