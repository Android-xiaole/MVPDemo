package com.lee.mvpdemo.ui;

import com.lee.base.mvp.BaseError;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.mvp.rxjava.ObservableSubscriber;
import com.lee.mvpdemo.api.LeeApi;
import com.lee.mvpdemo.data.bean.AuthBankCardBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Author ：le
 * Date ：2019-11-19 17:50
 * Description ：
 */
public class NetDemoPresenter extends BasePresenter<NetDemoContract.View> implements NetDemoContract.Presenter {

    @Override
    public void testApi() {
        getView().showProgress();
        LeeApi.getInstance().testApi()
                .subscribeOn(Schedulers.io())
                .map(ResponseBody::string)
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new ObservableSubscriber<String>() {
                    @Override
                    protected void onFail(BaseError error) {
                        getView().onTestApi("error:\n" + error.getMessage());
                    }

                    @Override
                    public void onNext(String result) {
                        getView().onTestApi("通过map操作符转换成String:\n" + result);
                    }

                    @Override
                    protected void onEnd() {
                        getView().dismissProgress();
                    }
                });
    }

    @Override
    public void testApi2() {
        getView().showProgress();
        LeeApi.getInstance().testApi2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new ObservableSubscriber<String>() {
                    @Override
                    protected void onFail(BaseError error) {
                        getView().onTestApi("error:\n" + error.getMessage());
                    }

                    @Override
                    public void onNext(String result) {
                        getView().onTestApi("直接返回String:\n" + result);
                    }

                    @Override
                    protected void onEnd() {
                        getView().dismissProgress();
                    }
                });
    }

    @Override
    public void testApi3() {
        getView().showProgress();
        LeeApi.getInstance().testApi3("6222600260001072444")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new ObservableSubscriber<AuthBankCardBean>() {
                    @Override
                    protected void onFail(BaseError error) {
                        getView().onTestApi("Json解析成类 error:\n" + error.getMessage());
                    }

                    @Override
                    public void onNext(AuthBankCardBean result) {
                        getView().onTestApi("Json解析成类:\n" + result.toString());
                    }

                    @Override
                    protected void onEnd() {
                        getView().dismissProgress();
                    }
                });
    }
}
