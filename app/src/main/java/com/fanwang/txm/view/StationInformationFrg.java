package com.fanwang.txm.view;

import android.os.Bundle;
import android.view.View;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.databinding.FStationBinding;
import com.fanwang.txm.presenter.StationInformationPresenter;
import com.fanwang.txm.view.impl.StationInformationContract;

/**
 * 作者：yc on 2018/8/24.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  工位信息
 */

public class StationInformationFrg extends BaseFragment<StationInformationPresenter, FStationBinding> implements StationInformationContract.View{

    public static StationInformationFrg newInstance() {
        Bundle args = new Bundle();
        StationInformationFrg fragment = new StationInformationFrg();
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
        return R.layout.f_station;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.login_top));
        mB.refreshLayout.setPureScrollModeOn();
    }
}
