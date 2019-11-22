package com.lee.base;

/**
 * Author ：le
 * Date ：2019-10-21 18:07
 * Description ：静态常量变量存储类
 */
public class BaseConstants {

    /**
     * 实际使用的时候可以保存在共享参数里面,这样就可以动态改变，
     * 并且不用担心application被销毁重启之后静态变量被重置的情况
     */
    public static String BASE_URL = "https://ccdcapi.alipay.com";
    public static final String URL_IGNORE = "#url_ignore";//此标识忽略动态替换baseurl配置

}
