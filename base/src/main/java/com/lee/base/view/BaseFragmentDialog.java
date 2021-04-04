package com.lee.base.view;


import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 *  DialogFragment为google官方推荐使用的dialog，其生命周期与activity绑定，方便管理
 */
public class BaseFragmentDialog extends DialogFragment {

    private DialogInterface.OnDismissListener onDismissListener;

    public void show(FragmentManager manager) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            manager.executePendingTransactions();//此方法是为了isAdded返回值同步
            if (isAdded()){
                //如果已经添加了当前的fragment并且不可见，那就直接show出来
                if (!isVisible()){
                    ft.show(this);
                }
            }else{
                //第二个tag参数是用来标记当前Fragment的，为了使用方便这里直接封装一下，默认取该类名
                ft.add(this, getClass().getSimpleName());
                //不用commit方法是为了防止出现异常-Caused by: java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                ft.commitAllowingStateLoss();
                // 主动调用此方法是为了将mDismissed设置为false，防止出现调用show之后立马调用dissmiss方法弹窗无法消失的问题
                onAttach(getContext());
            }
        }catch (Exception e){
            //这里抓取一下异常，防止特殊情况下add报错
        }
    }

    @Override
    public void dismiss() {
        //一样是为了防止出现异常-Caused by: java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        dismissAllowingStateLoss();
    }

    /**
     * 这个主要是用于外部调用的时候可以将dismiss的监听回调到外部处理
     */
    public void addDismissListener(DialogInterface.OnDismissListener onDismissListener){
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener!=null)onDismissListener.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (onDismissListener!=null){
            onDismissListener = null;
        }
    }
}
