package com.fanwang.txm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.fanwang.txm.base.BaseMainFragment;
import com.fanwang.txm.event.CameraInEvent;
import com.fanwang.txm.event.TabSelectedEvent;
import com.fanwang.txm.view.MonitorFrg;
import com.fanwang.txm.view.QueryFrg;
import com.fanwang.txm.view.WorkFrg;
import com.fanwang.txm.view.ZhihuFirstFragment;
import com.fanwang.txm.view.ZhihuSecondFragment;
import com.fanwang.txm.view.ZhihuThirdFragment;
import com.fanwang.txm.weight.buttonBar.BottomBar;
import com.fanwang.txm.weight.buttonBar.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class MainActivity extends SwipeBackActivity implements BaseMainFragment.OnBackToFirstListener{


    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    private SupportFragment[] mFragments = new SupportFragment[3];

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明导航栏
        setContentView(R.layout.f_main);

        mBottomBar = findViewById(R.id.bottomBar);

        SupportFragment firstFragment = findFragment(ZhihuFirstFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = ZhihuFirstFragment.newInstance();
            mFragments[SECOND] = ZhihuSecondFragment.newInstance();
            mFragments[THIRD] = ZhihuThirdFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(ZhihuSecondFragment.class);
            mFragments[THIRD] = findFragment(ZhihuThirdFragment.class);
        }


        mBottomBar
                .addItem(new BottomBarTab(this, R.mipmap.maininterface_icon_monito, "监控"))
                .addItem(new BottomBarTab(this, R.mipmap.monitor_icon_monitor_icon_monitor_click, "查询"))
                .addItem(new BottomBarTab(this, R.mipmap.monitor_icon_workposition, "工位"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (position == 0){
                    mBottomBar.setCurrentUnSelectedPosition(0, true);
                }
                EventBus.getDefault().post(new TabSelectedEvent(true));
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
                if (position == 0){
                    mBottomBar.setCurrentUnSelectedPosition(0, true);
                }
                EventBus.getDefault().post(new TabSelectedEvent(true));
            }

            @Override
            public void onTabReselected(int position) {
                if (position == 0){
                    mBottomBar.setCurrentUnSelectedPosition(0, true);
                }
                EventBus.getDefault().post(new TabSelectedEvent(true));
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof ZhihuFirstFragment) {
                        currentFragment.popToChild(MonitorFrg.class, false);
                    } else if (currentFragment instanceof ZhihuSecondFragment) {
                        currentFragment.popToChild(QueryFrg.class, false);
                    } else if (currentFragment instanceof ZhihuThirdFragment) {
                        currentFragment.popToChild(WorkFrg.class, false);
                    }
                    return;
                }

            }
        });
//        mBottomBar.setCurrentItem(0);
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
        setSwipeBackEnable(false);

        mBottomBar.setCurrentUnSelectedPosition(0, false);
    }


    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }


    public FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }


    /**
     * Android 点击EditText文本框之外任何地方隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {

                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(new CameraInEvent(requestCode, data));
        }
    }
}
