package com.home.androidjavamap;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.home.androidjavamap.entities.PlaceApiResponse;
import com.home.androidjavamap.entities.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository {

    final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    final String GOOGLE_PLACE_API_KEY = "AIzaSyCwmYvGIV7owfcc7muneajVaIz6cXKA8Wg";

    private MutableLiveData<List<Result>> result = new MutableLiveData<>();

    public LiveData<List<Result>> getResult(){
        return result;
    }

    public void getData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<PlaceApiResponse> call = service.getTextResults("restaurantes en lima", GOOGLE_PLACE_API_KEY);
        call.enqueue(new Callback<PlaceApiResponse>() {
            @Override
            public void onResponse(Call<PlaceApiResponse> call, Response<PlaceApiResponse> response) {
                if (response.isSuccessful()) {
                    PlaceApiResponse res = response.body();
                    result.setValue(res.getResults());
                }
            }

            @Override
            public void onFailure(Call<PlaceApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
