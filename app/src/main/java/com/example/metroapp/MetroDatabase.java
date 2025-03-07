package com.example.metroapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Station.class}, version = 1, exportSchema = false)
public abstract class MetroDatabase extends RoomDatabase {
    private static volatile MetroDatabase INSTANCE;

    public abstract StationDao stationDao();

    public static MetroDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MetroDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MetroDatabase.class, "metro_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
