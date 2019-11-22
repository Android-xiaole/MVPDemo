package com.lee.mvpdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lee.base.ui.BaseActivity;
import com.lee.base.ui.BaseFragment;
import com.lee.mvpdemo.LeeConstants;
import com.lee.mvpdemo.R;
import com.lee.mvpdemo.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author ：le
 * Date ：2019-11-19 15:17
 * Description ：
 */
@Route(path = LeeConstants.RoutePath.APP_NETDEMO_ACTIVITY)
public class NetDemoFragment extends BaseFragment<NetDemoPresenter> implements NetDemoContract.View {

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_netdemo;
    }

    @Override
    protected NetDemoPresenter setPresenter() {
        return new NetDemoPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @OnClick({R2.id.btn_test_api, R2.id.btn_test_api2, R2.id.btn_test_api3})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_test_api:
                getPresenter().testApi();
                break;

            case R.id.btn_test_api2:
                getPresenter().testApi2();
                break;

            case R.id.btn_test_api3:
                getPresenter().testApi3();
                break;
        }
    }

    @Override
    public void onTestApi(String result) {
        tv_result.setText(result);
    }

}
