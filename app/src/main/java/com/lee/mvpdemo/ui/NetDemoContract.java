package com.lee.mvpdemo.ui;

import com.lee.base.mvp.BaseContract;

/**
 * Author ：le
 * Date ：2019-11-19 17:50
 * Description ：
 */
public interface NetDemoContract {
    interface Presenter {

        void testApi();

        void testApi2();

        void testApi3();

        void testApi4();
    }

    interface View extends BaseContract.IView{

        void onTestApi(String result);

    }
}
