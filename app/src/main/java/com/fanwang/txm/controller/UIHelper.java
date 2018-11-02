package com.fanwang.txm.controller;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.fanwang.txm.MainActivity;
import com.fanwang.txm.WeChatCaptureActivity;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.view.ForgetPwdFrg;
import com.fanwang.txm.view.LoginFrg;
import com.fanwang.txm.view.ProductInformationFrg;
import com.fanwang.txm.view.QueryFrg;
import com.fanwang.txm.view.SettingFrg;
import com.fanwang.txm.view.StationInformationFrg;
import com.fanwang.txm.view.UpdateNameFrg;
import com.fanwang.txm.view.UpdatePwdFrg;


/**
 * Created by Administrator on 2017/2/22.
 */

public final class UIHelper {

    private UIHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startMainAct() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     *  忘记密码
     * @param root
     */
    public static void startForgetPwdFrg(BaseFragment root) {
        root.start(new ForgetPwdFrg());
    }

    /**
     *  物料追踪
     */
    public static void startProductInformationFrg(BaseFragment root, int type) {
        ProductInformationFrg frg = ProductInformationFrg.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  设置
     * @param root
     */
    public static void startSettingFrg(BaseFragment root) {
        root.start(SettingFrg.newInstance());
    }

    /**
     *  修改公司名称
     */
    public static void startUpdateNameFrg(BaseFragment root) {
        root.start(UpdateNameFrg.newInstance());
    }

    /**
     *  密码修改
     * @param root
     */
    public static void startUpdatePwdFrg(BaseFragment root) {
        root.start(UpdatePwdFrg.newInstance());
    }

    /**
     *  二维码扫描
     */
    public static void startZxingAct(){
        ActivityUtils.startActivity(WeChatCaptureActivity.class);
    }

    /**
     *  工位信息
     * @param root
     */
    public static void startStationInformationFrg(BaseFragment root) {
        root.start(StationInformationFrg.newInstance());
    }
}