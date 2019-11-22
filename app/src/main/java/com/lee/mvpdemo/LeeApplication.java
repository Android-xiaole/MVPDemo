package com.lee.mvpdemo;

import com.lee.base.BaseApplication;

public class LeeApplication extends BaseApplication {

    @Override
    protected void init() {
        //init something
    }

    @Override
    protected boolean isDebug() {
        return LeeConstants.IS_DEBUG;
    }

}
