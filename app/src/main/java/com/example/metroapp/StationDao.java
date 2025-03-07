package com.example.metroapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Station> stations);

    @Query("SELECT * FROM stations")
    List<Station> getAllStations();

    @Query("DELETE FROM stations")
    void deleteAll();
}
