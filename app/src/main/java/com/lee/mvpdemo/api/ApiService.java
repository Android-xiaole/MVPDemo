package com.lee.mvpdemo.api;

import com.lee.mvpdemo.data.bean.AuthBankCardBean;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author ：le
 * Date ：2019-11-14 18:12
 * Description ：接口定义的集中地
 */
public interface ApiService {

    @GET("http://www.baidu.com")
    Observable<ResponseBody> testApi();

    @GET("https://www.baidu.com")
    Observable<String> testApi2();

    @GET("/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true")
    Observable<AuthBankCardBean> testApi3(@Query("cardNo") String cardNo);
}
