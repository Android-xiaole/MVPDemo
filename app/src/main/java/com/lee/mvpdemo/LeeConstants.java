package com.lee.mvpdemo;

import com.lee.base.BaseConstants;

/**
 * Author ：le
 * Date ：2019-11-18 13:45
 * Description ：静态常量变量存储类
 */
public class LeeConstants extends BaseConstants {

    public static final boolean IS_DEBUG = true;

    /**
     * 建议path命名规则：module名_业务名_type
     */
    public static final class RoutePath{
        public static final String APP_NETDEMO_ACTIVITY = "/ui/NetDemoFragment";
        public static final String APP_DBDEMO_ACTIVITY = "/ui/DbDemoFragment";
    }
}
