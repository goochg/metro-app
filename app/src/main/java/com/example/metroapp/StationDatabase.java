package com.example.metroapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Station.class}, version = 1)
public abstract class StationDatabase extends RoomDatabase {
    private static StationDatabase instance;

    public abstract StationDao stationDao();

    public static synchronized StationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            StationDatabase.class, "stations_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
