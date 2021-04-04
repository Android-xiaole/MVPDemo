package com.lee.mvpdemo.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.ui.BaseFragment;
import com.lee.mvpdemo.LeeConstants;
import com.lee.mvpdemo.R;

import androidx.fragment.app.FragmentTransaction;

/**
 * Author ：le
 * Date ：2019-11-19 15:18
 * Description ：
 */
@Route(path = LeeConstants.RoutePath.APP_DBDEMO_FRAGMENT)
public class DbDemoFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dbdemo;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tv_des","DbDemoFragment childFragment");
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected boolean onInterceptTouchEvent() {
        showShortToast("拦截了点击事件");
        return true;
    }

    @Override
    protected boolean onBackPressed() {
        showShortToast(this.getClass().getSimpleName()+"：页面触发了返回键");
        return true;
    }
}
