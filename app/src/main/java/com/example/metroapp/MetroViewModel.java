package com.example.metroapp.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.metroapp.MetroApiService;
import com.example.metroapp.RetrofitClient;
import com.example.metroapp.model.MetroTime;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetroViewModel extends AndroidViewModel {
    private final Application application;

    public MetroViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void fetchMetroTimes() {
        SharedPreferences prefs = application.getSharedPreferences("metro_prefs", Context.MODE_PRIVATE);
        //String outboundStation = prefs.getString("outbound_station", "SGF"); // Default SGF
        String stationShortcode = prefs.getString("selectedStationShortcode", "SGF");

        Log.d("MetroApp", "Fetching Metro Times for station: " + stationShortcode);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://metro-rti.nexus.org.uk/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MetroApiService apiService = retrofit.create(MetroApiService.class);
        Call<List<MetroTime>> call = apiService.getMetroTimes(stationShortcode);

        call.enqueue(new Callback<List<MetroTime>>() {
            @Override
            public void onResponse(@NonNull Call<List<MetroTime>> call, @NonNull Response<List<MetroTime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MetroApp", "Metro times fetched successfully!");
                    // TODO: Update LiveData to notify UI
                } else {
                    Log.e("MetroApp", "MetroViewModel> FetchMetroTimes> API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MetroTime>> call, @NonNull Throwable t) {
                Log.e("MetroApp", "API request error", t);
            }
        });
    }
}
