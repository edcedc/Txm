package com.fanwang.txm.presenter;

import com.fanwang.txm.view.impl.SettingContract;

/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class SettingPresenter extends SettingContract.Persenter{

    @Override
    public void ajaxImage(String imgUrl) {
        mView.showImage(imgUrl);
    }

    @Override
    public void ajaxLogo(String logoUrl) {
        mView.showLogo(logoUrl);
    }

}
