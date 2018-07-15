package com.naver.hyeonjung.navertestapp.network;


import android.support.annotation.WorkerThread;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.naver.hyeonjung.navertestapp.network.base.BaseApiService;
import com.naver.hyeonjung.navertestapp.network.base.RestError;
import com.naver.hyeonjung.navertestapp.network.code.ResponseCodes;
import com.naver.hyeonjung.navertestapp.vo.Image;
import com.naver.hyeonjung.navertestapp.vo.Video;
import com.naver.hyeonjung.navertestapp.vo.Web;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchManager extends BaseApiService {

    private static SearchManager instance;
    private SearchApi mSearchApi;


    private SearchManager() {
        super();
        mSearchApi = mRetrofitGenerator.createService(SearchApi.class);
    }

    public static SearchManager getInstance() {
        if (instance == null) {
            instance = new SearchManager();
        }
        return instance;
    }


    public void searchImage(String keyword, String sort, int start, int display, final GetTaskCallBack<List<Image>> callBack) {
        Call<JsonObject> call = mSearchApi.getImages(keyword, sort, start, display);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                int statusCode = response.code();

                if (statusCode == ResponseCodes.OK.getCode() && response.body() != null) {

                    ResultData resultData = new Gson().fromJson(response.body(), ResultData.class);
                    callBack.onTaskLoaded(resultData.getItems(Image.class));

                } else {
                    if (statusCode == ResponseCodes.BAD_REQUEST.getCode()) {
                        try {
                            RestError restError = mRetrofitGenerator.responseError(response.errorBody());
                            if (restError.getError_description() == null) {
                                callBack.onDataNotAvailable(getResponse(statusCode));
                            } else if (restError.getError_description().equals(ResponseCodes.PAGE_IS_MAX.getDesc())) {
                                callBack.onDataNotAvailable(ResponseCodes.PAGE_IS_MAX);
                            } else if (restError.getError_description().equals(ResponseCodes.SIZE_IS_MAX.getDesc())) {
                                callBack.onDataNotAvailable(ResponseCodes.SIZE_IS_MAX);
                            }
                        } catch (IOException e) {
                            callBack.onDataNotAvailable(getResponse(statusCode));
                        }
                    }
                    callBack.onDataNotAvailable(getResponse(statusCode));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onDataNotAvailable(ResponseCodes.CODE_UNKNOWN);

            }
        });
    }


    public void searchWeb(String keyword, int start, int display, final GetTaskCallBack<List<Web>> callBack) {


        Call<JsonObject> call = mSearchApi.getWeb(keyword, start, display);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                int statusCode = response.code();

                if (statusCode == ResponseCodes.OK.getCode() && response.body() != null) {

                    ResultData resultData = new Gson().fromJson(response.body(), ResultData.class);
                    callBack.onTaskLoaded(resultData.getItems(Web.class));

                } else {
                    if (statusCode == ResponseCodes.BAD_REQUEST.getCode()) {
                        try {
                            RestError restError = mRetrofitGenerator.responseError(response.errorBody());
                            if (restError.getError_description() == null) {
                                callBack.onDataNotAvailable(getResponse(statusCode));
                            } else if (restError.getError_description().equals(ResponseCodes.PAGE_IS_MAX.getDesc())) {
                                callBack.onDataNotAvailable(ResponseCodes.PAGE_IS_MAX);
                            } else if (restError.getError_description().equals(ResponseCodes.SIZE_IS_MAX.getDesc())) {
                                callBack.onDataNotAvailable(ResponseCodes.SIZE_IS_MAX);
                            }
                        } catch (IOException e) {
                            callBack.onDataNotAvailable(getResponse(statusCode));
                        }
                    }
                    callBack.onDataNotAvailable(getResponse(statusCode));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onDataNotAvailable(ResponseCodes.CODE_UNKNOWN);

            }
        });
    }

    public void destroy() {
        instance = null;
    }

}
