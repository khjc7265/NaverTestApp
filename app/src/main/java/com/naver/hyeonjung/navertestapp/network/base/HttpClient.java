package com.naver.hyeonjung.navertestapp.network.base;


import com.naver.hyeonjung.navertestapp.Const;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.naver.hyeonjung.navertestapp.BuildConfig.IS_DEV;


public class HttpClient {

    private static HttpClient instance;

    private final int CONNECT_TIMEOUT = 15;
    private final int WRITE_TIMEOUT = 15;
    private final int READ_TIMEOUT = 15;

    private HttpLoggingInterceptor mHttpLogingInterceptor;
    private CookieManager mCookieManager;
    private OkHttpClient mClient;

    private HttpClient() {

        mHttpLogingInterceptor = new HttpLoggingInterceptor();
        mHttpLogingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        mClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(mCookieManager))
                .addInterceptor(new HeaderInterceptor())
                .build();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }

        return instance;
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    private class HeaderInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Naver-Client-Id", "la1TsAzRYnHkRkp_4ba3")
                    .addHeader("X-Naver-Client-Secret", "qozzjLCO67");

            requestBuilder.method(request.method(),request.body());
            return chain.proceed(requestBuilder.build());
        }
    }
}
