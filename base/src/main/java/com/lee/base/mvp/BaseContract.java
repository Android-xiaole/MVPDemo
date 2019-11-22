package com.lee.base.mvp;

import android.content.DialogInterface;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：P层和V层基础接口的合并类
 */
public interface BaseContract {

    interface IPresenter<V extends IView>{
        V getView();

        void attachView(V v);

        void detachView();
    }

    interface IView{
        default void showProgress(){

        }

        default void showProgress(DialogInterface.OnDismissListener onDismissListener){

        }

        default void dismissProgress(){

        }

        default void showShortToast(CharSequence msg){
            ToastUtils.showShort(msg);
        }

        default void showLongToast(CharSequence msg){
            ToastUtils.showLong(msg);
        }
    }
}
