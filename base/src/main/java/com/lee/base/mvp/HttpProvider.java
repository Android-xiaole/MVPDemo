package com.lee.base.mvp;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;

/**
 * Author ：le
 * Date ：2019-11-05 10:29
 * Description ：设置http相关请求的提供者类
 */
public interface HttpProvider {

    String configBaseUrl();//配置网络请求框架的baseurl

    Interceptor[] configInterceptors();//配置拦截器

    Converter.Factory[] configConverterFactories();//配置转换器

    void configOkHttpClient(OkHttpClient.Builder builder);//配置okhttp相关设置，可以进行任何设置

    default boolean configSupHttps(){//配置是否支持默认的https请求，默认支持，如果有自签名的证书验证，可以设置false，在configHttps里面对证书进行单独设置
        return true;
    }

    CookieJar configCookie();//配置cookie

    RequestHandler configHandler();//对网络请求进行拦截设置

    long configConnectTimeoutMills();//设置连接时长，不设置就默认10s

    long configReadTimeoutMills();//设置读取时长，不设置就默认10s

    boolean configLogEnable();//设置是否打印日志，日志包含请求和响应的相关参数及返回值
}
