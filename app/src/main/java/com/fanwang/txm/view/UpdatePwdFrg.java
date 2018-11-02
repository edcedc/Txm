package com.fanwang.txm.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseView;
import com.fanwang.txm.databinding.FUpdatePwdBinding;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  密码修改
 */

public class UpdatePwdFrg extends BaseFragment<BasePresenter, FUpdatePwdBinding> implements IBaseView{

    public static UpdatePwdFrg newInstance() {
        Bundle args = new Bundle();
        UpdatePwdFrg fragment = new UpdatePwdFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_update_pwd;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.login_top));



        mB.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwd = mB.etOldPwd.getText().toString().trim();
                String newPwd = mB.etNewPwd.getText().toString().trim();
                if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
                    showToast(getString(R.string.error_pwd));
                    return;
                }

            }
        });
    }
}
