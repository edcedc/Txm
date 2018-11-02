package com.fanwang.txm.presenter;

import android.os.Handler;

import com.fanwang.txm.bean.DataBean;
import com.fanwang.txm.view.impl.WorkContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class WorkPresenter extends WorkContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> listBean = new ArrayList<>();
                for (int i = 0;i < 15;i++){
                    listBean.add(new DataBean());
                }
                mView.setData(listBean);
            }
        }, 2000);
    }

}
