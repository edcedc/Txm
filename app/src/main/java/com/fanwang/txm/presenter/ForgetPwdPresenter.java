package com.fanwang.txm.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fanwang.txm.R;
import com.fanwang.txm.bean.BaseResponseBean;
import com.fanwang.txm.callback.Code;
import com.fanwang.txm.controller.CloudApi;
import com.fanwang.txm.view.impl.ForgetPwdContract;
import com.lzy.okgo.model.Response;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/8/21.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class ForgetPwdPresenter extends ForgetPwdContract.Presenter {

    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)){
            ToastUtils.showShort(act.getString(R.string.error_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(act.getString(R.string.error_phone1));
            return;
        }
        CloudApi.userGetRegisterCode("", phone)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.OnCodeSuccess();
                        }
                        ToastUtils.showShort(baseResponseBeanResponse.body().desc);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });


    }

    @Override
    public void forget(final String phone, String code, final String pwd, String pwd1) {
        if (StringUtils.isEmpty(phone)){
            ToastUtils.showShort(act.getString(R.string.error_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort(act.getString(R.string.error_phone1));
            return;
        }
        if (StringUtils.isEmpty(code)){
            ToastUtils.showShort(act.getString(R.string.error_code));
            return;
        }
        if (StringUtils.isEmpty(pwd)){
            ToastUtils.showShort(act.getString(R.string.error_pwd));
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16){
            ToastUtils.showShort(act.getString(R.string.error_pwd2));
            return;
        }
        CloudApi.userUpdateForgetPassword(phone,code, pwd)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.onSuccess(phone, pwd);
                        }
                        ToastUtils.showShort(baseResponseBeanResponse.body().desc);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

}
