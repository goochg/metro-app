//package com.example.metroapp;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class MetroRepository {
//    private static final String BASE_URL = "https://metro-rti.nexus.org.uk/api/";
//    private final MetroApiService apiService;
//    private final StationDao stationDao;
//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//    public MetroRepository(Context context) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiService = retrofit.create(MetroApiService.class);
//        MetroDatabase db = MetroDatabase.getInstance(context);
//        stationDao = db.stationDao();
//    }
//
//    // Fetch stations from API and store in Room database
//    public void fetchAndStoreStations() {
//        apiService.getStations().enqueue(new Callback<Map<String, String>>() {
//            @Override
//            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    storeStationsLocally(response.body());
//                } else {
//                    Log.e("MetroRepository", "Failed to fetch stations: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
//                Log.e("MetroRepository", "API call failed", t);
//            }
//        });
//    }
//
//    // Store stations in Room
//    private void storeStationsLocally(Map<String, String> stationMap) {
//        List<Station> stationList = new ArrayList<>();
//        for (Map.Entry<String, String> entry : stationMap.entrySet()) {
//            stationList.add(new Station(entry.getKey(), entry.getValue()));
//        }
//
//        executorService.execute(() -> {
//            stationDao.deleteAll(); // Clear old data
//            stationDao.insertAll(stationList);
//        });
//    }
//
//    // Retrieve stations from Room
//    public List<Station> getStoredStations() {
//        return stationDao.getAllStations();
//    }
//}
