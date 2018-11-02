package com.fanwang.txm.utils;

import android.content.Context;

/**
 * 作者：yc on 2018/8/18.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class ShareIsLogin {


    private static SharedPreferencesTool share;

    private ShareIsLogin() {
    }

    private static ShareIsLogin instance = null;


    public static ShareIsLogin getInstance(Context context) {
        if (instance == null) {
            instance = new ShareIsLogin();
        }
        share = SharedPreferencesTool.getInstance(context, "login");
        return instance;
    }

    private final String IS_LOGIN = "login";

    public void save(boolean isLogin) {
        share.putBoolean(IS_LOGIN, isLogin);
        share.commit();
    }
    public void delete(){
        share.remove(IS_LOGIN);
        share.commit();
    }

    public boolean getIS_LOGIN() {
        return share.getBoolean(IS_LOGIN, false);
    }
}
