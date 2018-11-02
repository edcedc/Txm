package com.fanwang.txm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseFragment;
import com.fanwang.txm.controller.UIHelper;
import com.fanwang.txm.databinding.FSetBinding;
import com.fanwang.txm.event.CameraInEvent;
import com.fanwang.txm.presenter.SettingPresenter;
import com.fanwang.txm.utils.PictureSelectorTool;
import com.fanwang.txm.view.bottomFrg.CameraBottomFrg;
import com.fanwang.txm.view.impl.SettingContract;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  设置
 */

public class SettingFrg extends BaseFragment<SettingPresenter, FSetBinding> implements SettingContract.View, View.OnClickListener{

    public static SettingFrg newInstance() {
        Bundle args = new Bundle();
        SettingFrg fragment = new SettingFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_set;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.login_top));
        mB.ivImage.setOnClickListener(this);
        mB.ivLogo.setOnClickListener(this);
        mB.lyName.setOnClickListener(this);
        mB.lyPwd.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getCutPath();
        if (event.getRequest() == CameraInEvent.SET_IMAGE_CAMEAR || event.getRequest() == CameraInEvent.SET_IMAGE_PHOTO){
            mPresenter.ajaxImage(path);
        }else {
            mPresenter.ajaxLogo(path);
        }
        LogUtils.e(path);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_image:
                showCamear(CameraInEvent.SET_IMAGE_CAMEAR, CameraInEvent.SET_IMAGE_PHOTO, false);
                break;
            case R.id.iv_logo:
                showCamear(CameraInEvent.SET_LOGO_CAMEAR, CameraInEvent.SET_LOGO_PHOTO, true);
                break;
            case R.id.ly_name:
                UIHelper.startUpdateNameFrg(this);
                break;
            case R.id.ly_pwd:
                UIHelper.startUpdatePwdFrg(this);
                break;
        }
    }

    private void showCamear(final int camear, final int photo, final boolean circleDimmedLayer){
        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg();
        }
        cameraBottomFrg.show(getChildFragmentManager(), "dialog");
        cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
            @Override
            public void camera() {
                PictureSelectorTool.PictureSelectorImage(act, camear, circleDimmedLayer);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void photo() {
                PictureSelectorTool.photo(act, photo, circleDimmedLayer);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }
        });
    }

    @Override
    public void showImage(String path) {
        Glide.with(act).load(path).into(mB.ivImage);
    }

    @Override
    public void showLogo(String path) {
        Glide.with(act).load(path).into(mB.ivLogo);
    }
}
