package com.example.metroapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = AppDatabase.getInstance(this);
        fetchAndStoreStations();
    }

    private void fetchAndStoreStations() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://metro-rti.nexus.org.uk/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MetroApiService apiService = retrofit.create(MetroApiService.class);

        Call<Map<String, String>> call = apiService.getStations();

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Station> stationList = response.body().entrySet().stream()
                            .map(entry -> new Station(entry.getKey(), entry.getValue()))
                            .toList();

                    new Thread(() -> {
                        db.stationDao().insertAll(stationList);
                        Log.d("MetroApp", "Stations inserted into the database!");
                        runOnUiThread(() -> populateDropdowns());

                    }).start();
                } else {
                    Log.e("MetroApp", "SettingsActivity>> API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Log.e("MetroApp", "API request error", t);
            }
        });
    }

    private void populateDropdowns() {
        new Thread(() -> {
            List<Station> stationList = db.stationDao().getAllStations();
            if (stationList.isEmpty()) {
                Log.e("MetroApp", "No stations found in the database!");
                return;
            }

            List<String> stationNames = new ArrayList<>();
            for (Station station : stationList) {
                stationNames.add(station.getName());
            }

            runOnUiThread(() -> {
                setupSpinner(R.id.spinner_outbound, stationNames, "outbound_station");
                setupSpinner(R.id.spinner_inbound, stationNames, "inbound_station");
            });
        }).start();
    }

    private void setupSpinner(int spinnerId, List<String> items, String prefKey) {
        Spinner spinner = findViewById(spinnerId);
        SharedPreferences prefs = getSharedPreferences("metro_prefs", MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Load saved value
        String savedStation = prefs.getString(prefKey, null);
        if (savedStation != null) {
            int position = items.indexOf(savedStation);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }

        // Save selected station
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStation = items.get(position);
                prefs.edit().putString(prefKey, selectedStation).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


}
