package com.fanwang.txm.base;

import android.content.Context;

/**
 * 作者：yc on 2018/8/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public abstract class BasePresenter<T> {

    public Context act;

    public T mView;

    public void init(T v) {
        this.mView = v;
    }



}
