package com.fanwang.txm.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.fanwang.txm.MainActivity;
import com.fanwang.txm.R;
import com.fanwang.txm.adapter.MontiorAdater;
import com.fanwang.txm.adapter.WorkAdapter;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.base.BasePresenter;
import com.fanwang.txm.bean.DataBean;
import com.fanwang.txm.databinding.FMonitorBinding;
import com.fanwang.txm.event.TabSelectedEvent;
import com.fanwang.txm.presenter.MonitorPresenter;
import com.fanwang.txm.utils.PopupWindowTool;
import com.fanwang.txm.view.impl.MonitorContarct;
import com.fanwang.txm.weight.LinearDividerItemDecoration;
import com.fanwang.txm.weight.MonitorHeadView;
import com.ganxin.library.LoadDataLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 * 监控
 */

public class MonitorFrg extends BaseFragment<MonitorPresenter, FMonitorBinding> implements MonitorContarct.View {

    public static MonitorFrg newInstance() {
        Bundle args = new Bundle();
        MonitorFrg fragment = new MonitorFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listType = new ArrayList<>();//机型
    private List<DataBean> listWork = new ArrayList<>();//工位

    private MontiorAdater adapter;
    private MontiorAdater adapter1;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_monitor;
    }

    @Override
    protected void initView(View view) {
        setCenterTitle(getString(R.string.login_top), R.mipmap.monitor_menu);
        if (adapter == null) {
            adapter = new MontiorAdater(act, listType);
        }
        if (adapter1 == null) {
            adapter1 = new MontiorAdater(act, listWork);
        }
        setRecyclerView(mB.recyclerView, adapter);
        setRecyclerView(mB.recyclerView1, adapter1);
        mB.refreshLayout.setEnableLoadmore(false);
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
                mPresenter.onRequest2(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber += 1);
                mPresenter.onRequest2(pagerNumber += 1);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(TabSelectedEvent event){
        if (mB.ivImg.getVisibility() == View.VISIBLE && event.isTab){
            mB.ivImg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
//        super.setRefreshLayoutMode(listType.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        if (pagerNumber == 1) {
            listType.clear();
            DataBean dataBean = new DataBean();
            dataBean.setType(0);
            listType.add(dataBean);
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listType.addAll((List<DataBean>) data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setData2(List<DataBean> listBean) {
        if (pagerNumber == 1) {
            listWork.clear();
            DataBean dataBean = new DataBean();
            dataBean.setType(1);
            listWork.add(dataBean);
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listWork.addAll(listBean);
        adapter1.notifyDataSetChanged();
    }

    private void setRecyclerView(RecyclerView recyclerView, MontiorAdater adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        PopupWindowTool.showJurisdiction(act);
    }

}
