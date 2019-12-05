package com.lee.mvpdemo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.lee.base.mvp.BasePresenter;
import com.lee.base.ui.BaseFragment;
import com.lee.mvpdemo.R;
import com.lee.mvpdemo.R2;

import butterknife.BindView;

/**
 * Author ：lee
 * Date ：2019/11/26 16:55
 * Description ：
 */
public class CommonFragment extends BaseFragment {

    @BindView(R2.id.tv_des)
    TextView tv_des;

    private String des;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            des = bundle.getString("tv_des");
            tv_des.setText(des);
        }
    }

    @Override
    protected boolean onBackPressed() {
        showShortToast(des+"：页面触发了返回键");
        return true;
    }
}
