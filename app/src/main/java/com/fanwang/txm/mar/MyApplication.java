package com.fanwang.txm.mar;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.fanwang.txm.service.InitializeService;
import com.nanchen.crashmanager.CrashApplication;

public class MyApplication extends CrashApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //        LogUtils.getConfig().setLogSwitch(false);
        InitializeService.start(this);
        // 设置崩溃后自动重启 APP
//        UncaughtExceptionHandlerImpl.getInstance().init(this, BuildConfig.DEBUG, true, 0, MainActivity.class);

    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }


}
