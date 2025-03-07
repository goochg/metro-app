package com.example.metroapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.metroapp.R;
import com.example.metroapp.model.MetroTime;
import java.util.ArrayList;
import java.util.List;

public class MetroAdapter extends RecyclerView.Adapter<MetroAdapter.MetroViewHolder> {
    private Context context;
    private List<MetroTime> metroTimes = new ArrayList<>();

    public MetroAdapter (Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MetroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MetroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetroViewHolder holder, int position) {
        MetroTime metroTime = metroTimes.get(position);
        setLabelTextColor(holder.itemView, ContextCompat.getColor(context, R.color.white));

        holder.destination.setTextColor(ContextCompat.getColor(context, R.color.text_grey));
        holder.destination.setText(metroTime.getDestination());
        holder.dueIn.setTextColor(ContextCompat.getColor(context, R.color.text_grey));
        holder.dueIn.setText(String.format("%d min", metroTime.getDueIn()));
        holder.line.setText(metroTime.getLine());
        holder.line.setTextColor(metroTime.getLineColour(context));
    }

    @Override
    public int getItemCount() {
        return metroTimes.size();
    }

    public void setMetroTimes(List<MetroTime> metroTimes) {
        this.metroTimes = metroTimes;
        notifyDataSetChanged();
    }

    public void setLabelTextColor(View parentView, int color) {
        // Check if the parent view is a ViewGroup (like LinearLayout)
        if (parentView instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) parentView;

            // Loop through all child views of the parent layout
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);

                // If the child is a ViewGroup, recursively check its children
                if (child instanceof ViewGroup) {
                    setLabelTextColor(child, color);
                } else if (child instanceof TextView) {
                    TextView textView = (TextView) child;

                    // Check if the TextView ID ends with "label"
                    String resourceName = textView.getResources().getResourceEntryName(textView.getId());
                    if (resourceName.endsWith("label")) {
                        // Set the text color for this TextView
                        textView.setTextColor(color);
                    }
                }
            }
        }
    }

    static class MetroViewHolder extends RecyclerView.ViewHolder {
        TextView destination, dueIn, line;

        MetroViewHolder(View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destination);
            dueIn = itemView.findViewById(R.id.dueIn);
            line = itemView.findViewById(R.id.line);
        }
    }
}
