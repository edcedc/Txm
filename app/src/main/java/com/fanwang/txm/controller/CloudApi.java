package com.fanwang.txm.controller;

import com.fanwang.txm.bean.BaseResponseBean;
import com.fanwang.txm.callback.JsonConvert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {


    private static final String url =
            "10.0.0.199:8080" +
//            "112.74.162.5" +
                    "/";

    public static final String SERVLET_URL = "http://" +
            url + "chain/api/";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 通用获取验证码
     */
    public static Observable<Response<BaseResponseBean>> userGetRegisterCode(String url, String phone) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + url)
                .params("mobile", phone)
                .converter(new JsonConvert<BaseResponseBean>() {})
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 3.1.5忘记密码 接口(新)
     */
    public static Observable<Response<BaseResponseBean>> userUpdateForgetPassword(String phone, String code, String password) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "user/updateForgetPassword")
                .params("mobile", phone)
                .params("code", code)
                .params("password", password)
                .params("repeatPassword", password)
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }


}