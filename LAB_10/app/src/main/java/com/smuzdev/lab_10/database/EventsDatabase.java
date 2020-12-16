package com.smuzdev.lab_10.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.smuzdev.lab_10.dao.EventsDao;
import com.smuzdev.lab_10.model.Events;

@Database(entities = {Events.class}, version = 1)
public abstract class EventsDatabase extends RoomDatabase {

    private static final String DB_NAME = "FitDay.db";

    private static EventsDatabase instance;

    public abstract EventsDao eventsDao();

    public static EventsDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context,
                    EventsDatabase.class,
                    DB_NAME).build();
        }
        return instance;
    }
}
