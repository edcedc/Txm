package com.fanwang.txm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwang.txm.R;
import com.fanwang.txm.base.BaseMainFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class ZhihuThirdFragment extends BaseMainFragment {

    public static ZhihuThirdFragment newInstance() {

        Bundle args = new Bundle();

        ZhihuThirdFragment fragment = new ZhihuThirdFragment();
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

        if (findChildFragment(WorkFrg.class) == null) {
            // ShopFragment是flow包里的
            loadRootFragment(R.id.fl_container, WorkFrg.newInstance());
        }
    }
}
