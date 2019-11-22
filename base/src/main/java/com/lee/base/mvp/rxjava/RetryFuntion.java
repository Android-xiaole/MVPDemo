package com.lee.base.mvp.rxjava;

import android.os.NetworkOnMainThreadException;

import com.blankj.utilcode.util.LogUtils;
import com.lee.base.mvp.BaseError;
import com.lee.base.mvp.HttpCode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Function;

/**
 * Author ：le
 * Date ：2019-10-22 14:54
 * Description ：rxjava重试机制类
 */
public class RetryFuntion implements Function<Observable<Throwable>, ObservableSource<?>> {
    private int maxConnectCount = 2;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    private String retryTag = "";//非必须，只是为了方便查看log，异常产生于哪里

    public RetryFuntion(String retryTag) {
        this.retryTag = retryTag;
    }

    public RetryFuntion() {
    }


    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable){
        return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            LogUtils.e(retryTag + "发生异常 = " + throwable.toString());
            boolean needRetry = throwable instanceof IOException;
            if (!needRetry && throwable instanceof CompositeException) {
                List<Throwable> exceptions = ((CompositeException) throwable).getExceptions();
                for (Throwable exception : exceptions) {
                    needRetry = exception instanceof IOException;
                    if (needRetry) break;
                }
            }
            if (needRetry) {
                LogUtils.e(retryTag + "属于IO异常，需重试");
                if (currentRetryCount < maxConnectCount) {
                    currentRetryCount++;
                    LogUtils.e(retryTag + "重试次数 = " + currentRetryCount);

                    waitRetryTime = 1000 + currentRetryCount * 1000;
                    LogUtils.d(retryTag+"等待时间 =" + waitRetryTime);
                    return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
                } else {
                    // 若重试次数已 > 设置重试次数，则不重试
                    // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                    return Observable.error(new BaseError(throwable));
                }
            } else if (throwable instanceof BaseError) {
                return Observable.error(throwable);
            } else if (throwable instanceof NetworkOnMainThreadException) {
                return Observable.error(new BaseError(HttpCode.OTHER_ERROR.getCode(), "NetworkOnMainThreadException"));
            }
            return Observable.error(new BaseError(throwable));
        });
    }
}
