package com.naver.hyeonjung.navertestapp.network.base;


import com.naver.hyeonjung.navertestapp.network.code.ResponseCodes;

public class BaseApiService {

    public interface GetTaskCallBack<T> {

        void onTaskLoaded(T task);

        void onDataNotAvailable(ResponseCodes codes);
    }

    public interface GetEmptyCallBack {

        void onTaskLoaded();

        void onDataNotAvailable(ResponseCodes codes);
    }

    public RetrofitServiceGenerator mRetrofitGenerator;
    private HttpClient mClient;

    public BaseApiService() {
        mClient = HttpClient.getInstance();
        mRetrofitGenerator = new RetrofitServiceGenerator(mClient.getClient());
    }

    public ResponseCodes getResponse(int statusCode){
        for( ResponseCodes responseCode : ResponseCodes.values()){
            if(responseCode.getCode() == statusCode){
                return responseCode;
            }
        }

        return ResponseCodes.CODE_UNKNOWN;
    }

}
