package com.lee.base.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.lee.base.mvp.interceptor.HttpLogInterceptor;
import com.lee.base.mvp.interceptor.HttpRequestInterceptor;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Author ：le
 * Date ：2019-11-14 15:14
 * Description ：
 */
public abstract class BaseApi {

    //连接时间默认10s
    private static final long connectTimeoutMills = 10 * 10000;
    //读取时间默认10s
    private static final long readTimeoutMills = 10 * 10000;

    public abstract HttpProvider configHttpProvider();

    private void checkProvider(HttpProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must config httpProvider first");
        }
    }

    /**
     * 获取Retrofit对象
     *
     * @link https://www.taobye.com/f/view-4-66.html
     * java 四种线程池介绍，这里采用newCachedThreadPool
     */
    public Retrofit getRetrofit() {
        HttpProvider httpProvider = configHttpProvider();
        checkProvider(httpProvider);

        //配置线程池模式
        ExecutorService executorService = Executors.newCachedThreadPool();

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(getClient(httpProvider))
                .callbackExecutor(executorService)
                .baseUrl(httpProvider.configBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        //设置外部额外配置的自定义转换器
        Converter.Factory[] factories = httpProvider.configConverterFactories();
        if (factories != null && factories.length > 0) {
            for (Converter.Factory factory : factories) {
                builder.addConverterFactory(factory);
            }
        }

         /*
            添加将ResponseBody转换成原始数据的转换器，
            你的返回值可以是String/Boolean/Long...或者其对应的基本数据类型
            因为一般接口默认都是json格式，会直接转换成类对象，
            所以这种设置就单独设置比较好，默认设置没必要
          */
        builder.addConverterFactory(ScalarsConverterFactory.create());

        //配置解析器gson设置
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeHierarchyAdapter(String.class, STRING)//设置解析的时候null转成""
                .create();
        //最后添加默认的gson转换器，因为GsonConverterFactory的源码在转换失败的时候没有返回null，导致如果这一层解析失败走不到下一个解析器
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }

    /**
     * 创建okhttp client
     */
    private OkHttpClient getClient(HttpProvider provider) {
        checkProvider(provider);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //配置连接响应时间和读取响应时间
        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);

        //配置cookie
        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }

        //支持访问https
        if (provider.configSupHttps()) {
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                X509TrustManager trustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
                X509TrustManager[] trustManagers = new X509TrustManager[]{
                        trustManager
                };
                sc.init(null, trustManagers, new SecureRandom());
                builder.sslSocketFactory(sc.getSocketFactory(), trustManager)
                        .hostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                LogUtils.e("设置支持访问https失败：" + e.getMessage());
            }
        }

        //这里将builder对象抛出去，方便对OkHttpClient进行任意设置
        provider.configOkHttpClient(builder);

        //添加手动配置的拦截器
        Interceptor[] interceptors = provider.configInterceptors();
        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //添加修改请求参数和响应体的拦截器
        RequestHandler handler = provider.configHandler();
        if (handler != null) {
            builder.addInterceptor(new HttpRequestInterceptor(handler));
        }

        //最后添加打印log日志的拦截器，设置只有调试模式才打印
        if (provider.configLogEnable()) {
            builder.addInterceptor(new HttpLogInterceptor());
        }

        return builder.build();
    }


    /**
     * 自定义TypeAdapter ,null对象将被解析成空字符串
     */
    private static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return ""; // 原先是返回null，这里改为返回空字符串
                }
                return reader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void write(JsonWriter writer, String value) {
            try {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
