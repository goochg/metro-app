package com.example.metroapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.concurrent.Executors;

public class StationSelectionActivity extends AppCompatActivity {
    private ListView stationListView;
    private StationDatabase stationDatabase;
    private List<Station> stationList;
    private String selectedStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_selection);

        stationDatabase = StationDatabase.getInstance(this);
        stationListView = findViewById(R.id.stationListView);
        Button saveButton = findViewById(R.id.saveButton);

        loadStations();

        stationListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedStation = stationList.get(position).name;
        });

        saveButton.setOnClickListener(v -> {
            if (selectedStation != null) {
                // Find the selected station object
                for (Station station : stationList) {
                    if (station.name.equals(selectedStation)) {
                        // Save both name and shortcode
                        getSharedPreferences("MetroPrefs", MODE_PRIVATE)
                                .edit()
                                .putString("selectedStationName", station.name)
                                .putString("selectedStationShortcode", station.shortcode)
                                .apply();

                        finish();
                        break;
                    }
                }
            }
        });

    }

    private void loadStations() {
        Executors.newSingleThreadExecutor().execute(() -> {
            stationList = stationDatabase.stationDao().getAllStations();
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice);
                for (Station station : stationList) {
                    adapter.add(station.name);
                }
                stationListView.setAdapter(adapter);
            });
        });
    }
}
