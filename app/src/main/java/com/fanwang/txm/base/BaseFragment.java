/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwang.txm.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.controller.UIHelper;
import com.fanwang.txm.utils.TUtil;
import com.fanwang.txm.view.MainFrg;
import com.fanwang.txm.view.impl.LoginView;
import com.ganxin.library.LoadDataLayout;
import com.ganxin.library.SwipeLoadDataLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.yanzhenjie.sofia.Sofia;
import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * -
 * -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 * ------可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 * -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 */

/**
 * ================================================
 * 作    者：yc）
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseFragment<P extends BasePresenter, VB extends ViewDataBinding> extends SwipeBackFragment {

    private boolean isVisible = true;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载
    protected LayoutInflater inflater;
    protected Activity act;
    private View view;

    protected VB mB;
    public P mPresenter;
    private LoadDataLayout swipeLoadDataLayout;


    protected int pagerNumber = 1;//网络请求默认第一页
    protected int mTotalPage;//网络请求当前几页
    protected int TOTAL_COUNTER;//网络请求一共有几页

    protected boolean isTopFrg = false;//记录是否onResum导航栏


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d("onCreateView");
        this.inflater = inflater;
        isFirstLoad = true;

        View rootView = getLayoutInflater().inflate(this.bindLayout(), null, false);
        mB = DataBindingUtil.bind(rootView);
        isPrepared = true;
        // 初始化参数
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        initParms(bundle);
        this.view = rootView;
        initView(rootView);
        swipeLoadDataLayout = view.findViewById(R.id.swipeLoadDataLayout);
        mPresenter = TUtil.getT(this, 0);
        if(mPresenter!=null){
            mPresenter.act = this.getActivity();
            this.initPresenter();
        }
        return attachToSwipeBack(rootView);
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    protected void setSofia(boolean isFullScreen) {
        if (!isFullScreen){
           Sofia.with(act)
                .statusBarLightFont()
                   .invasionStatusBar()
                   .statusBarBackgroundAlpha(0)
                   .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(act, R.color.gray_EFF0F0))
           ;
        }else {
            Sofia.with(act)
                    .invasionStatusBar()
//                    .invasionNavigationBar()
                    .statusBarDarkFont()
                    .statusBarBackgroundAlpha(0)
            ;
        }
    }


    protected abstract void initParms(Bundle bundle);

    protected abstract int bindLayout();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate");
        act = getActivity();
    }

    protected void showToast(final String str){
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
//                ToastUtils.showLong(str);
                ToastUtils.showShort(str);
            }
        });
    }


    private ProgressDialog dialog;

    public void showLoading() {
        mHandler.sendEmptyMessage(handler_load);
    }

    public void hideLoading() {
        mHandler.sendEmptyMessage(handler_hide);
    }

    public void onError(Throwable e) {
        if (null != e){
            mHandler.sendEmptyMessage(handler_hide);
            LogUtils.e(e.getMessage());
            showToast(e.getMessage());
        }
    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    private final int handler_load = 0;
    private final int handler_hide = 1;
    private final int handler_empty = 2;
    private final int handler_error = 3;
    private final int handler_no_network = 4;
    private final int handler_loadData = 5;
    private final int handler_success = 6;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case handler_load:
                    if (dialog != null && dialog.isShowing()) return;
                    dialog = new ProgressDialog(act);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("请求网络中...");
                    dialog.show();
                    break;
                case handler_hide:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
                case handler_empty:
                    swipeLoadDataLayout.setStatus(LoadDataLayout.EMPTY);
                    break;
                case handler_loadData:
                    swipeLoadDataLayout.setStatus(LoadDataLayout.LOADING);
                    break;
                case handler_success:
                    swipeLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);
                    break;
            }
        }
    };

    protected void hideLoad2ing(){
        mHandler.sendEmptyMessage(handler_success);
    }

    protected void showLoadDataing(){
        mHandler.sendEmptyMessage(handler_loadData);
    }

    protected void showLoadEmpty(){
        mHandler.sendEmptyMessage(handler_empty);
    }

    protected void setRefreshLayout(TwinklingRefreshLayout refreshLayout, RefreshListenerAdapter listener){
//        ProgressLayout headerView = new ProgressLayout(act);
//        refreshLayout.setHeaderView(headerView);
//        refreshLayout.setOverScrollRefreshShow(false);
        ProgressLayout header = new ProgressLayout(act);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(240);
        refreshLayout.setOverScrollHeight(200);
        refreshLayout.setOnRefreshListener(listener);
    }

    protected void setRefreshLayoutMode(int listSize, int totalRow, TwinklingRefreshLayout refreshLayout){
        if (listSize == totalRow) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }
    }

    protected void setRefreshLayout(final int pagerNumber, final TwinklingRefreshLayout refreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pagerNumber == 1) {
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }            }
        },300);

    }


    protected void setOnRightClickListener() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.d("onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.d("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
        LogUtils.d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.d("onDetach");
    }


    protected void setCenterTitle(String title){
        title(title, 0, null, -1);
    }
      protected void setCenterTitle(String title, int img){
        title(title, 0, null, img);
    }

    protected void setTitle(String title){
        title(title, 1, null, -1);
    }
    protected void setTitle(String title, String right){
        title(title, 2, right, -1);
    }
    protected void setTitle(String title, int rightImg){
        title(title, 1, null, rightImg);
    }

    private void title(String title, int type, String rightText, int img) {
        setSofia(false);
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView topTitle = view.findViewById(R.id.top_title);
        TextView topRight = view.findViewById(R.id.top_right);
        FrameLayout topRightFy = view.findViewById(R.id.top_right_fy);
        ImageView ivRight = view.findViewById(R.id.iv_right);
        //需要调用该函数才能设置toolbar的信息
        mAppCompatActivity.setSupportActionBar(toolbar);
        switch (type){
            case 0:
                mAppCompatActivity.getSupportActionBar().setTitle("");
                topTitle.setVisibility(View.VISIBLE);
                topTitle.setText(title);
                toolbar.setNavigationIcon(null);
                if (img != -1){
                    ivRight.setBackgroundResource(img);
                    ivRight.setVisibility(View.VISIBLE);
                    topRight.setVisibility(View.GONE);
                    topRightFy.setVisibility(View.VISIBLE);
                    topRightFy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setOnRightClickListener();
                        }
                    });
                }
                break;
            case 1:
                if (img != -1){
                    topTitle.setVisibility(View.GONE);
                    topRight.setVisibility(View.VISIBLE);
                    topRight.setBackgroundResource(img);
                    topRightFy.setVisibility(View.VISIBLE);
                }
                mAppCompatActivity.getSupportActionBar().setTitle("");
                topTitle.setVisibility(View.VISIBLE);
                topTitle.setText(title);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        act.onBackPressed();
                    }
                });
                topRightFy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setOnRightClickListener();
                    }
                });
                break;
            case 2:
                topTitle.setVisibility(View.GONE);
                mAppCompatActivity.getSupportActionBar().setTitle(title);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        act.onBackPressed();
                    }
                });
                topRight.setText(rightText);
                topRightFy.setVisibility(View.VISIBLE);
                topRightFy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setOnRightClickListener();
                    }
                });
                break;
        }
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime;

    protected boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }




    private Handler adapterHandler = new Handler();
    protected void setRefreshListener(EasyRecyclerView recyclerView, final RecyclerArrayAdapter adapter){
        refreshListener(recyclerView, adapter, true);
    }
    protected void setRefreshListener(EasyRecyclerView recyclerView, final RecyclerArrayAdapter adapter, boolean isLoadMore){
        refreshListener(recyclerView, adapter, isLoadMore);
    }

    private void refreshListener(EasyRecyclerView recyclerView, final RecyclerArrayAdapter adapter, boolean isLoadMore){
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onEasyRefresh();
                    }
                }, 500);
            }
        });
        if (isLoadMore){
            adapter.setError(R.layout.view_empty, new RecyclerArrayAdapter.OnErrorListener() {
                @Override
                public void onErrorShow() {
                    adapter.resumeMore();
                }

                @Override
                public void onErrorClick() {
                    adapter.resumeMore();
                }
            });
            adapter.setNoMore(R.layout.view_nomore);
            adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    adapterHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onEasyLoadMore();
                        }
                    }, 500);
                }
            });
        }
    }

    protected void onEasyLoadMore(){
    };

    protected void onEasyRefresh(){
    };

}
