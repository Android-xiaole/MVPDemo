package com.lee.base.mvp.rxjava;

import com.lee.base.mvp.BaseError;
import com.lee.base.mvp.IModel;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;

/**
 * Author ：le
 * Date ：2019-11-14 14:30
 * Description ：rxjava处理错误数据的方法集合类
 */
public class ModelTransformer {

    /**
     * Flowable异常处理变换
     */
    public static <T extends IModel> FlowableTransformer<T, T> getFlowableTransformer() {

        return upstream -> upstream.flatMap((Function<T, Publisher<T>>) model -> {
            if (model == null) {
                return Flowable.error(BaseError.nullDataError());
            } else if (model.error() != null) {
                return Flowable.error(model.error());
            } else {
                return Flowable.just(model);
            }
        });
    }

    /**
     * Observable异常处理变换
     */
    public static <T extends IModel> ObservableTransformer<T, T> getObservableTransformer() {

        return upstream -> upstream.flatMap((Function<T, ObservableSource<T>>) model -> {
            if (model == null) {
                return Observable.error(BaseError.nullDataError());
            } else if (model.error() != null) {
                return Observable.error(model.error());
            } else {
                return Observable.just(model);
            }
        });
    }

    public static <T extends IModel> SingleTransformer<T, T> getSingleTransformer() {

        return upstream -> upstream.flatMap((Function<T, SingleSource<? extends T>>) model -> {
            if (model == null) {
                return Single.error(BaseError.nullDataError());
            } else if (model.error() != null) {
                return Single.error(model.error());
            } else {
                return Single.just(model);
            }
        });
    }
}
