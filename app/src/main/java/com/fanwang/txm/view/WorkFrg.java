package com.fanwang.txm.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.adapter.ImageAdapter;
import com.fanwang.txm.adapter.WorkAdapter;
import com.fanwang.txm.adapter.WorkViewHolder;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.bean.DataBean;
import com.fanwang.txm.controller.UIHelper;
import com.fanwang.txm.databinding.BRecyclerBinding;
import com.fanwang.txm.event.CameraInEvent;
import com.fanwang.txm.presenter.WorkPresenter;
import com.fanwang.txm.utils.PictureSelectorTool;
import com.fanwang.txm.view.bottomFrg.CameraBottomFrg;
import com.fanwang.txm.view.impl.WorkContract;
import com.fanwang.txm.weight.LinearDividerItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  工位
 */

public class WorkFrg extends BaseFragment<WorkPresenter, BRecyclerBinding> implements WorkContract.View{

    public static WorkFrg newInstance() {
        Bundle args = new Bundle();
        WorkFrg fragment = new WorkFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private WorkAdapter adapter;
    private ImageAdapter imageAdapter;
    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String imgUrl;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @Override
    protected void initView(View view) {
        setCenterTitle(getString(R.string.login_top), R.mipmap.monitor_menu);
        EventBus.getDefault().register(this);
        if (adapter == null){
            adapter = new WorkAdapter(act, listBean);
        }
        mB.recyclerView.setLayoutManager(new LinearLayoutManager(act));
        mB.recyclerView.setHasFixedSize(true);
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 10, Color.parseColor("#eff0f0")));
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(true);
        mB.refreshLayout.startRefresh();
        showLoadDataing();
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

        adapter.setOnClickCameraListener(new WorkViewHolder.OnClickCameraListener() {
            @Override
            public void onClick(int position) {
                if (cameraBottomFrg == null){
                    cameraBottomFrg = new CameraBottomFrg();
                }
                cameraBottomFrg.show(getChildFragmentManager(), "dialog");
                cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
                    @Override
                    public void camera() {
                        PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.WORK_CAMEAR);
                    }

                    @Override
                    public void photo() {
                        PictureSelectorTool.photo(act, CameraInEvent.WORK_PHOTO);
                    }
                });
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        for (LocalMedia media : localMediaList){
            imgUrl = media.getCompressPath();
        }
        LogUtils.e(imgUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        hideLoad2ing();
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            if (list.size() != 0){
                listBean.clear();
            }else {
                showLoadEmpty();
            }
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
//        PopupWindowTool.showJurisdiction(act);
        UIHelper.startSettingFrg(this);
    }

}
