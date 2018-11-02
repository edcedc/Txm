package com.fanwang.txm.view;

import android.os.Bundle;
import android.view.View;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.base.IBaseView;
import com.fanwang.txm.controller.UIHelper;
import com.fanwang.txm.databinding.FQueryBinding;
import com.fanwang.txm.presenter.QueryPresenter;
import com.fanwang.txm.utils.PopupWindowTool;
import com.fanwang.txm.view.impl.QueryContract;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  查询
 */

public class QueryFrg extends BaseFragment<QueryPresenter, FQueryBinding> implements QueryContract.View, View.OnClickListener{

    public static QueryFrg newInstance() {
        Bundle args = new Bundle();
        QueryFrg fragment = new QueryFrg();
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
        return R.layout.f_query;
    }

    @Override
    protected void initView(View view) {
        setCenterTitle(getString(R.string.login_top), R.mipmap.monitor_menu);
        mB.ivMateriel.setOnClickListener(this);
        mB.ivProduct.setOnClickListener(this);
        mB.ivStatistics.setOnClickListener(this);
        mB.ivWork.setOnClickListener(this);
        mB.fyZxing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_materiel://物料追踪
                UIHelper.startProductInformationFrg(this, ProductInformationFrg.LOGISTICS_TRACKING);
                break;
            case R.id.iv_product://产品信息
                UIHelper.startProductInformationFrg(this, ProductInformationFrg.PRODUCT_INFORMATION);
                break;
            case R.id.iv_work://工位信息
                UIHelper.startStationInformationFrg(this);
                break;
            case R.id.iv_statistics://统计报表
                UIHelper.startProductInformationFrg(this, ProductInformationFrg.STATISTICAL_REPORT);
                break;
            case R.id.fy_zxing://二维码
                UIHelper.startZxingAct();
                break;
        }
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
//        PopupWindowTool.showJurisdiction(act);
        UIHelper.startSettingFrg(this);
    }

}
