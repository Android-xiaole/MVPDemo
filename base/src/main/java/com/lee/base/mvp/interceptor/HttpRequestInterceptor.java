package com.lee.base.mvp.interceptor;

import com.lee.base.mvp.RequestHandler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author ：le
 * Date ：2019-11-14 17:00
 * Description ：
 */
public class HttpRequestInterceptor implements Interceptor {

    private RequestHandler handler;

    public HttpRequestInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            response = handler.onAfterRequest(response, chain);
        }
        return response;
    }
}
