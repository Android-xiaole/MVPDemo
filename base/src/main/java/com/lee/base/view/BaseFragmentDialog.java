package com.lee.base.view;


import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 *  关于解决DialogFragment可能存在内存泄漏的问题（参考）（测试发现在一个界面重复调用show会频繁出现内存泄漏，但是帖子针对的都是说安卓5.0以下才会出现的问题）
 *
 *  @link https://segmentfault.com/q/1010000017286787/a-1020000017322905
 *  @link https://www.cnblogs.com/endure/p/7664320.html
 *
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

    //    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        boolean isShow = this.getShowsDialog();
//        this.setShowsDialog(false);
//        super.onActivityCreated(savedInstanceState);
//        this.setShowsDialog(isShow);
//        //以上代码是为了调用fragment的super.onActivityCreated(savedInstanceState);
//
//        if (isShow) {
//            //以下代码是取消调用了super里面的两个监听
//            // this.mDialog.setOnCancelListener(this);
//            // this.mDialog.setOnDismissListener(this);
//            //  这样dialog就不默认实现这两个监听，如果需要监听需要外部手动设置
//            View view = this.getView();
//            if (view != null) {
//                if (view.getParent() != null) {
//                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
//                }
//
//                this.getDialog().setContentView(view);
//            }
//
//            Activity activity = this.getActivity();
//            if (activity != null) {
//                this.getDialog().setOwnerActivity(activity);
//            }
//
//            this.getDialog().setCancelable(this.isCancelable());
//            if (savedInstanceState != null) {
//                Bundle dialogState = savedInstanceState.getBundle("android:savedDialogState");
//                if (dialogState != null) {
//                    this.getDialog().onRestoreInstanceState(dialogState);
//                }
//            }
//        }
//    }

}
