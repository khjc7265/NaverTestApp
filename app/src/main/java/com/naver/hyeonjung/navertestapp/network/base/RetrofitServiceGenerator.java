package com.naver.hyeonjung.navertestapp.network.base;



import com.naver.hyeonjung.navertestapp.Const;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {
    private Retrofit.Builder mBuilder;

    RetrofitServiceGenerator(OkHttpClient client){
        mBuilder = new Retrofit.Builder()
                .baseUrl(Const.SERVER_DOMAIN)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public <T> T createService(Class<T> serviceClass){
        Retrofit retrofit = mBuilder.build();
        return retrofit.create(serviceClass);
    }

    public RestError responseError(ResponseBody errorBody) throws IOException {
        Converter<ResponseBody, RestError> errorConverter = mBuilder.build().responseBodyConverter(RestError.class,new Annotation[0]);
        return errorConverter.convert(errorBody);

    }
}
