package com.lee.mvpdemo;

import com.lee.base.BaseConstants;

/**
 * Author ：le
 * Date ：2019-11-18 13:45
 * Description ：静态常量变量存储类
 */
public class LeeConstants extends BaseConstants {

    //debug变量也可以通过gradle的多渠道打包的方式，通过AndroidManifest的meta-data标签获取配置值，这样就不用担心打生产包的时候还需要修改debug值
    public static final boolean IS_DEBUG = true;

    /**
     * 建议path命名规则：module名_业务名_type
     */
    public static final class RoutePath{
        public static final String APP_NETDEMO_FRAGMENT = "/app/ui/NetDemoFragment";
        public static final String APP_DBDEMO_FRAGMENT = "/app/ui/DbDemoFragment";
    }
}
