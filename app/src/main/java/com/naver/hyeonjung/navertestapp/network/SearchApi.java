package com.naver.hyeonjung.navertestapp.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("/v1/search/image.json")
    Call<JsonObject> getImages(
            @Query("query") String query,
            @Query("sort") String sort,
            @Query("start") int start,
            @Query("display") int display
    );


    @GET("/v1/search/webkr.json")
    Call<JsonObject> getWeb(
            @Query("query") String query,
            @Query("start") int start,
            @Query("display") int display);
}
