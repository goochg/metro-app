package com.example.metroapp;

import com.example.metroapp.model.MetroTime;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MetroApiService {
    @GET("stations")
    Call<Map<String, String>> getStations();
    @GET("times/{station}/1")
    Call<List<MetroTime>> getMetroTimes(@Path("station") String station);;


}
