package com.lee.base.mvp.rxjava;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.JsonParseException;
import com.lee.base.mvp.BaseError;

import org.json.JSONException;

import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Author ：le
 * Date ：2019-10-22 14:09
 * Description ：
 */
public abstract class FlowableSubscriber extends ResourceSubscriber {
    /**
     * 网络请求结束的回调（不管成功还是失败都会回调，这里一般可以去做progress dismiss的操作）
     * 这和onComplete不同，onComplete只会在onNext走完之后回调,该方法不需要可以不调用
     */
    protected void onEnd() {}

    @Override
    protected void onStart() {
        super.onStart();
        NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
        switch (networkType) {
            case NETWORK_NO:
            case NETWORK_UNKNOWN:
                // 一定好主动调用下面这一句,取消本次Subscriber订阅
                if (!isDisposed()) {
                    dispose();
                }
                onFail(BaseError.netWorkError());
                onEnd();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        BaseError error;
        if (e != null) {
            if (!(e instanceof BaseError)) {
                if (e instanceof UnknownHostException) {
                    error = BaseError.netWorkError();
                } else if (e instanceof JSONException || e instanceof JsonParseException) {
                    error = BaseError.jsonParseError();
                } else {
                    error = new BaseError(e);
                }
            } else {
                error = (BaseError) e;
            }
        }else{
            //e=null，就设定为未知异常
            error = BaseError.unKnowError();
        }
        onFail(error);
        onEnd();
    }

    //网络请求失败回调
    protected abstract void onFail(BaseError error);

    @Override
    public void onComplete() {
        onEnd();
    }
}
