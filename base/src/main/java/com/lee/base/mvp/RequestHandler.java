package com.lee.base.mvp;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author ：le
 * Date ：2019-11-13 15:55
 * Description ：请求拦截处理类，常用来设置公共请求头
 */
public interface RequestHandler {

    default Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        return request;
    }

    default Response onAfterRequest(Response response, Interceptor.Chain chain) {
        return response;
    }
}
