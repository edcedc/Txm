package com.fanwang.txm.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.adapter.ProductAdapter;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.bean.DataBean;
import com.fanwang.txm.databinding.FProductInformationBinding;
import com.fanwang.txm.presenter.ProductPresenter;
import com.fanwang.txm.utils.TopRightMenuDataTool;
import com.fanwang.txm.view.impl.ProductContract;
import com.fanwang.txm.weight.LinearDividerItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zaaach.toprightmenu.TopRightMenuTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/22.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  产品信息
 */

public class ProductInformationFrg extends BaseFragment<ProductPresenter, FProductInformationBinding> implements ProductContract.View, View.OnClickListener{

    public static final int LOGISTICS_TRACKING = 0;//物料追踪
    public static final int PRODUCT_INFORMATION = 1;//产品信息
    public static final int STATISTICAL_REPORT = 2;//统计报表

    public static ProductInformationFrg newInstance() {
        Bundle args = new Bundle();
        ProductInformationFrg fragment = new ProductInformationFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int mType;//记录哪个页面跳转
    private List<DataBean> listBean = new ArrayList<>();
    private ProductAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        mType = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_product_information;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.login_top));
        switch (mType){
            case LOGISTICS_TRACKING:
                mB.gpTitle.setVisibility(View.GONE);
                mB.fyImg.setVisibility(View.GONE);
                break;
            case PRODUCT_INFORMATION:
                mB.gpTitle.setVisibility(View.VISIBLE);
                mB.fyImg.setVisibility(View.GONE);

                mB.tvTitle.setText("型号:201             条码:2514605602");
                break;
            case STATISTICAL_REPORT:
                mB.gpTitle.setVisibility(View.GONE);
                mB.fyImg.setVisibility(View.VISIBLE);

                mB.tvTotal.setText("100");
                mB.tvNumber.setText("100");
                mB.tvRate.setText("100");
                break;
        }
        mB.lyTime.setVisibility(View.VISIBLE);

        mB.tvStartYear.setOnClickListener(this);
        mB.tvStartMonth.setOnClickListener(this);
        mB.tvStartDay.setOnClickListener(this);
        mB.tvEndYear.setOnClickListener(this);
        mB.tvEndMonth.setOnClickListener(this);
        mB.tvEndDay.setOnClickListener(this);
        mB.ivSearch.setOnClickListener(this);
        if (adapter == null){
            adapter = new ProductAdapter(act, listBean);
        }
        mB.recyclerView.setLayoutManager(new LinearLayoutManager(act));
        mB.recyclerView.setHasFixedSize(true);
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber += 1);
            }
        });

    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            DataBean dataBean = new DataBean();
            dataBean.setType(0);
            listBean.add(dataBean);
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_start_year:
                mPresenter.getYear(mB.tvStartYear, mB.tvStartMonth, mB.tvStartDay);
                break;
            case R.id.tv_start_month:
                mPresenter.getMonth(mB.tvStartYear, mB.tvStartMonth, mB.tvStartDay);
                break;
            case R.id.tv_start_day:
                mPresenter.getDay(mB.tvStartYear, mB.tvStartMonth, mB.tvStartDay);
                break;
            case R.id.tv_end_year:
                mPresenter.getYear(mB.tvEndYear, mB.tvEndMonth, mB.tvEndDay);
                break;
            case R.id.tv_end_month:
                mPresenter.getMonth(mB.tvEndYear, mB.tvEndMonth, mB.tvEndDay);
                break;
            case R.id.tv_end_day:
                mPresenter.getDay(mB.tvEndYear, mB.tvEndMonth, mB.tvEndDay);
                break;
            case R.id.iv_search:
                mPresenter.search();
                break;
        }
    }
}
