package com.example.metroapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stations")
public class Station {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String shortcode;
    public String name;

    public Station(String shortcode, String name){
        this.shortcode = shortcode;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
