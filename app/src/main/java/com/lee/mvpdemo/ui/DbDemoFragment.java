package com.lee.mvpdemo.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lee.base.mvp.BasePresenter;
import com.lee.base.ui.BaseFragment;
import com.lee.mvpdemo.LeeConstants;
import com.lee.mvpdemo.R;

/**
 * Author ：le
 * Date ：2019-11-19 15:18
 * Description ：
 */
@Route(path = LeeConstants.RoutePath.APP_DBDEMO_ACTIVITY)
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

    }

    @Override
    protected boolean onBackPressed() {
        showShortToast(this.getClass().getSimpleName()+"：页面触发了返回键");
        return true;
    }
}
