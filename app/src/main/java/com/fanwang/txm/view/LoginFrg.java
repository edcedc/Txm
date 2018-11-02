package com.fanwang.txm.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.fanwang.txm.utils.SharedAccount;
import com.fanwang.txm.view.impl.LoginContract;
import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.controller.UIHelper;
import com.fanwang.txm.databinding.FLoginBinding;
import com.fanwang.txm.presenter.LoginPresenter;

/**
 * 作者：yc on 2018/8/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  登录
 */

public class LoginFrg extends BaseFragment<LoginPresenter, FLoginBinding> implements LoginContract.View, View.OnClickListener{

    public static LoginFrg newInstance() {
        Bundle args = new Bundle();
        LoginFrg fragment = new LoginFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_login;
    }

    @Override
    protected void initView(View view) {
        setCenterTitle(getString(R.string.login_top));
//        ShareIsLogin.getInstance(act).save(true);
        setSwipeBackEnable(false);
        mB.tvSubmit.setOnClickListener(this);
        mB.tvForgetPwd.setOnClickListener(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setSofia(false);
        SharedAccount instance = SharedAccount.getInstance(act);
        String mobile = instance.getMobile();
        String pwd = instance.getPassword();
        if (!StringUtils.isEmpty(mobile)){
            mB.etPhone.setText(mobile);
            if (!StringUtils.isEmpty(pwd)){
                mB.etPwd.setText(pwd);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
//                mPresenter.login(mB.etPhone.getText().toString().trim(), mB.etPwd.getText().toString().trim());
                loginSuccess();
                break;
            case R.id.tv_forget_pwd:
                UIHelper.startForgetPwdFrg(this);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        UIHelper.startMainAct();
    }

    @Override
    public boolean onBackPressedSupport() {
        ActivityUtils.finishAllActivities();
        return false;
    }
}
