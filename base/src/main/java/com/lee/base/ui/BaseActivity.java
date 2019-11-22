package com.lee.base.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.lee.base.mvp.BaseContract;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.view.BaseProgress;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：BaseActivity用来处理一些必须的逻辑
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseContract.IView, BackHandledFragment.BackHandledInterface {

    private P p;
    private Unbinder unbinder;
    private Bundle savedInstanceState;
    private boolean isStart;//标记当前activity已经走过一次onStart

    private BaseProgress dialog;
    private BackHandledFragment mBackHandedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
        }
        p = setPresenter();
        if (p != null) {
            //绑定MVP模式的View
            p.attachView(this);
            getLifecycle().addObserver(p);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         * 初始化数据的操作放这里是因为init里面可能有网络请求，会操作到当前界面的lifecycle对象，
         * 但发送当前生命周期到BasePresenter里面的时候会有一定的延时，从而导致BasePresenter的bindLifecycle操作空指针
         */
        if (!isStart) {
            initData(savedInstanceState);
        }
        isStart = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onBackPressed() {
        //如果当前存在BackHandedFragment，并且其不捕获onBackPressed事件
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * 判断当前页面是否载有BaseFragment
     *
     * @return true:有；false:没有
     */
    public boolean haseFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                //注：这里的fragment资料显示有可能为null,但不影响instanceof判断
                if (fragment instanceof BaseFragment) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setShowFragment(BackHandledFragment showFragment) {
        mBackHandedFragment = showFragment;
    }

    protected abstract int getLayoutId();

    protected abstract P setPresenter();

    protected abstract void initData(Bundle savedInstanceState);

    protected P getPresenter() {
        return p;
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new BaseProgress();
        }
        dialog.show(getSupportFragmentManager());
    }

    @Override
    public void showProgress(DialogInterface.OnDismissListener onDismissListener) {
        if (dialog == null) {
            dialog = new BaseProgress();
        }
        dialog.show(getSupportFragmentManager());
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
