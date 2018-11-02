package com.fanwang.txm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseMainFragment;

/**
 * 作者：yc on 2018/8/18.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MainFrg extends BaseMainFragment {

    public static MainFrg newInstance() {
        Bundle args = new Bundle();
        MainFrg fragment = new MainFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(MonitorFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MonitorFrg.newInstance());
        }
    }

}
