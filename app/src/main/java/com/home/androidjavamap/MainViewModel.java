package com.home.androidjavamap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.home.androidjavamap.entities.Result;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MainRepository repository = new MainRepository();
    private MutableLiveData<List<Result>> dataResult;

    MainViewModel() {

        dataResult = new MutableLiveData<>();
        repository.getResult().observeForever(new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                dataResult.setValue(results);
            }
        });
    }

    void getPlaces() {
        repository.getData();
    }

    LiveData<List<Result>> getDataResult() {
        return dataResult;
    }
}
