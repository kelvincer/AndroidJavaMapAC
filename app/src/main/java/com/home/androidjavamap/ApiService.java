package com.home.androidjavamap;

import com.home.androidjavamap.entities.PlaceApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("textsearch/json")
    Call<PlaceApiResponse> getTextResults(@Query("query") String query,           // required
                                          @Query("key") String key);          // required
}
