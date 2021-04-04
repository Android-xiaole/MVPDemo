package com.lee.base.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.lee.base.mvp.BaseContract;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.view.BaseProgress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：fragment的基类，初始化一些必要操作
 */
public abstract class BaseFragment<P extends BasePresenter> extends BackHandledFragment implements BaseContract.IView {

    private P p;
    private Unbinder unbinder;

    private BaseProgress dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = setPresenter();
        if (p != null) {
            //绑定MVP模式的View
            p.attachView(this);
            getLifecycle().addObserver(p);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (getArguments() != null)
                getArguments().putAll(savedInstanceState);
        }
        View view = null;
        if (getLayoutId() > 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        return view == null ? super.onCreateView(inflater, container, savedInstanceState) : view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract P setPresenter();

    protected abstract void initData(Bundle savedInstanceState);

    protected P getPresenter() {
        return p;
    }

    /**
     * 拦截点击事件的方法
     *
     * @return true:拦截 false:不拦截
     */
    protected boolean onInterceptTouchEvent(){
        return false;
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected boolean onBackPressed() {
        return false;//默认finish当前界面 true就是不finish
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new BaseProgress();
        }
        dialog.show(getChildFragmentManager());
    }

    @Override
    public void showProgress(DialogInterface.OnDismissListener onDismissListener) {
        showProgress();
        dialog.addDismissListener(onDismissListener);
    }

    @Override
    public void dismissProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showShortToast(CharSequence msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showLongToast(CharSequence msg) {
        ToastUtils.showLong(msg);
    }

}
