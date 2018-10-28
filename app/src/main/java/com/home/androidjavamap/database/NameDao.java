package com.home.androidjavamap.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NameDao {

    @Query("SELECT * FROM Name")
    LiveData<List<Name>> getAllNames();

    @Insert(onConflict = REPLACE)
    void addName(Name name);

    @Query("SELECT * FROM Name ORDER BY id DESC LIMIT 1")
    LiveData<Name> getLastName();
}
