package com.example.metroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metroapp.ui.MetroAdapter;
import com.example.metroapp.ui.MetroViewModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MetroViewModel viewModel = new ViewModelProvider(this).get(MetroViewModel.class);
        MetroAdapter adapter = new MetroAdapter(this);
        recyclerView.setAdapter(adapter);

        MetroViewModel metroViewModel = new ViewModelProvider(this).get(MetroViewModel.class);
        metroViewModel.fetchMetroTimes();

//        viewModel.getMetroTimes().observe(this, adapter::setMetroTimes);
    }


}
