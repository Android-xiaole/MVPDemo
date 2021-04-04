package com.lee.mvpdemo.ui;

import com.lee.base.mvp.BaseError;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.mvp.RequestBodyBuilder;
import com.lee.base.mvp.rxjava.NetWorkObserver;
import com.lee.mvpdemo.api.LeeApi;
import com.lee.mvpdemo.data.bean.AuthBankCardBean;
import com.lee.mvpdemo.data.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.ResourceObserver;
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
                .subscribe(new NetWorkObserver<String>() {

                    @Override
                    public void onSuccess(String result) {
                        getView().onTestApi("通过map操作符转换成String:\n" + result);
                    }

                    @Override
                    protected void onFailure(BaseError error) {
                        getView().onTestApi("error:\n" + error.getMessage());
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
                .subscribe(new NetWorkObserver<String>() {

                    @Override
                    public void onSuccess(@NonNull String result) {
                        getView().onTestApi("直接返回String:\n" + result);
                    }

                    @Override
                    protected void onFailure(BaseError error) {
                        getView().onTestApi("error:\n" + error.getMessage());
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
                .subscribe(new NetWorkObserver<AuthBankCardBean>() {

                    @Override
                    public void onSuccess(@NonNull AuthBankCardBean result) {
                        getView().onTestApi("Json解析成类:\n" + result.toString());
                    }

                    @Override
                    protected void onFailure(BaseError error) {
                        getView().onTestApi("Json解析成类 error:\n" + error.getMessage());
                    }

                    @Override
                    protected void onEnd() {
                        getView().dismissProgress();
                    }

                });
    }

    @Override
    public void testApi4() {
        Observable.just(0).map(integer -> {
            RequestBodyBuilder requestBodyBuilder = new RequestBodyBuilder();
            requestBodyBuilder.addProperty("Number", 1)
                    .addProperty("String", "abc")
                    .addProperty("boolean", true);

            List<String> strings = new ArrayList<>();
            strings.add("1");
            strings.add("2");
            strings.add("3");
            requestBodyBuilder.addProperty("ListString", strings);

            List<Integer> ints = new ArrayList<>();
            ints.add(1);
            ints.add(2);
            ints.add(3);
            requestBodyBuilder.addProperty("ListInt", ints);

            List<TestBean> models = new ArrayList<>();
            models.add(new TestBean(1,"1","1"));
            models.add(new TestBean(2,"2","2"));
            models.add(new TestBean(3,"3","3"));
            requestBodyBuilder.addProperty("ListObject", models);

            return requestBodyBuilder.toString();
        }).subscribe(new ResourceObserver<String>() {
            @Override
            public void onNext(@NonNull String result) {
                getView().onTestApi(result);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
