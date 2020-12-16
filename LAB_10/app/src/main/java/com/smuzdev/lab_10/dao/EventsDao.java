package com.smuzdev.lab_10.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.smuzdev.lab_10.model.Events;

import java.util.List;

@Dao
public interface EventsDao {

    @Query("select * from Events")
    LiveData<List<Events>> getAll();

    @Query("select * from Events where id = :id")
    Events getById(long id);

    @Insert
    void insert(Events events);

    @Update
    void update(Events events);

    @Delete
    void delete(Events events);
}
