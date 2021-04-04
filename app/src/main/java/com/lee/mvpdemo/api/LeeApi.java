package com.lee.mvpdemo.api;

import com.lee.base.BaseConstants;
import com.lee.base.mvp.BaseApi;
import com.lee.base.mvp.HttpProvider;
import com.lee.base.mvp.RequestHandler;
import com.lee.mvpdemo.LeeConstants;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;

/**
 * Author ：le
 * Date ：2019-11-14 18:00
 * Description ：适用于自己项目的BaseApi，可对HttpProvider进行相关设置，以及对ApiService初始化
 */
public class LeeApi extends BaseApi {

    private static ApiService apiService;

    private LeeApi() {
    }

    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (LeeApi.class) {
                if (apiService == null) {
                    apiService = new LeeApi().getRetrofit().create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    @Override
    public HttpProvider configHttpProvider() {
        return new HttpProvider() {
            @Override
            public String configBaseUrl() {
                return BaseConstants.BASE_URL;
            }

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public Converter.Factory[] configConverterFactories() {
                return new Converter.Factory[0];
            }

            @Override
            public void configOkHttpClient(OkHttpClient.Builder builder) {
            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public long configWriteTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return LeeConstants.IS_DEBUG;
            }

        };
    }
}
