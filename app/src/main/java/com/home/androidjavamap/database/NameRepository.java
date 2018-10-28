package com.home.androidjavamap.database;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class NameRepository {

    private NameDao nameDao;

    NameRepository(NameDao dao) {
        nameDao = dao;
    }

    LiveData<List<Name>> getNames() {
        return nameDao.getAllNames();
    }

    void insertName(Name name) {
        new InsertName(nameDao).execute(name, null, null);
    }

    LiveData<Name> getLastName(){
        return nameDao.getLastName();
    }

    private final static class InsertName extends AsyncTask<Name, Void, Void> {
        NameDao nameDao;

        InsertName(NameDao dao) {
            this.nameDao = dao;
        }

        @Override
        protected Void doInBackground(Name... names) {
            nameDao.addName(names[0]);
            return null;
        }
    }
}
