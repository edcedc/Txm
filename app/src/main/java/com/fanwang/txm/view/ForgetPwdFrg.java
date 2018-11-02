package com.fanwang.txm.view;

import android.os.Bundle;
import android.view.View;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.databinding.FForgetPwdBinding;
import com.fanwang.txm.presenter.ForgetPwdPresenter;
import com.fanwang.txm.utils.CountDownTimerUtils;
import com.fanwang.txm.utils.SharedAccount;
import com.fanwang.txm.view.impl.ForgetPwdContract;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  忘记密码
 */

public class ForgetPwdFrg extends BaseFragment<ForgetPwdPresenter, FForgetPwdBinding> implements ForgetPwdContract.View, View.OnClickListener{

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_forget_pwd;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.forget_pwd_top));
        mB.tvCode.setOnClickListener(this);
        mB.tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setSofia(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString().trim());
                break;
            case R.id.tv_submit:
                mPresenter.forget(mB.etPhone.getText().toString().trim(), mB.etCode.getText().toString().trim(), mB.etPwd.getText().toString().trim(), mB.etPwd2.getText().toString().trim());
                break;
        }
    }

    @Override
    public void OnCodeSuccess() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void onSuccess(String phone, String pwd) {
        SharedAccount.getInstance(act).save(phone, pwd);
    }
}
