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
        pathList.add(LeeConstants.RoutePath.APP_NETDEMO_FRAGMENT);
        pathList.add(LeeConstants.RoutePath.APP_DBDEMO_FRAGMENT);
        fragmentHelper = new FragmentHelper<>(this, pathList, R.id.fl_container);

        //这里可以添加带有参数的fragment
        CommonFragment commonFragment1 = new CommonFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tv_des","commonFragment1");
        commonFragment1.setArguments(bundle1);
        CommonFragment commonFragment2 = new CommonFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tv_des","commonFragment2");
        commonFragment2.setArguments(bundle2);
        CommonFragment commonFragment3 = new CommonFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tv_des","commonFragment3");
        commonFragment3.setArguments(bundle3);

        fragmentHelper.addFragment(commonFragment1);
        fragmentHelper.addFragment(commonFragment2);
        fragmentHelper.addFragment(commonFragment3);
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
