package com.example.metroapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.metroapp.R;
import com.example.metroapp.Station;
import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {
    private final List<Station> stationList;
    private String selectedStation;

    public StationAdapter(List<Station> stationList, String selectedStation) {
        this.stationList = stationList;
        this.selectedStation = selectedStation;
    }

    public String getSelectedStation() {
        return selectedStation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stationList.get(position);
        holder.stationName.setText(station.name);
        holder.checkBox.setChecked(station.shortcode.equals(selectedStation));

        holder.checkBox.setOnClickListener(v -> {
            selectedStation = station.shortcode;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stationName;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.stationName);
            checkBox = itemView.findViewById(R.id.stationCheckBox);
        }
    }
}
