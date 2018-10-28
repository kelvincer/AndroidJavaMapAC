package com.home.androidjavamap.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NameViewModel extends AndroidViewModel {

    private LiveData<List<Name>> names;
    private NameRepository nameRepository;
    private LiveData<Name> lastName;

    public NameViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getDatabase(this.getApplication());
        nameRepository = new NameRepository(appDatabase.getNameDao());
        names = nameRepository.getNames();
        lastName = nameRepository.getLastName();
    }

    LiveData<List<Name>> getNames() {
        return names;
    }

    LiveData<Name> getLastName() {
        return lastName;
    }

    void addName(Name name) {
        nameRepository.insertName(name);
    }
}
