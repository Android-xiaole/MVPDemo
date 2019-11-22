package com.lee.mvpdemo.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lee.base.FragmentHelper;
import com.lee.base.ui.BaseActivity;
import com.lee.mvpdemo.LeeConstants;
import com.lee.mvpdemo.R;
import com.lee.mvpdemo.R2;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R2.id.bottom_app_bar)
    BottomNavigationView bottomAppBar;

    private FragmentHelper<String> fragmentHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter setPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //设置这个代码是为了防止被选中icon的颜色跟随主题色变化
        bottomAppBar.setItemIconTintList(null);
        bottomAppBar.setOnNavigationItemSelectedListener(this);
        //init fragment
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(LeeConstants.RoutePath.APP_NETDEMO_ACTIVITY);
        pathList.add(LeeConstants.RoutePath.APP_DBDEMO_ACTIVITY);
        fragmentHelper = new FragmentHelper<>(this, pathList, R.id.fl_container);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_1:
                fragmentHelper.switchFragment(0);
                return true;
            case R.id.menu_home_2:
                fragmentHelper.switchFragment(1);
                return true;
            case R.id.menu_home_3:
                fragmentHelper.switchFragment(2);
                haseFragment();
                return true;
            case R.id.menu_home_4:
                fragmentHelper.switchFragment(3);
                return true;
            case R.id.menu_home_5:
                fragmentHelper.switchFragment(4);
                return true;
        }
        return false;
    }
}
