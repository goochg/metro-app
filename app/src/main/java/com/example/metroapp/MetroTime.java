package com.example.metroapp.model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.metroapp.R;

public class MetroTime {

    private String destination;
    private int dueIn;
    private String line;
     private String actualScheduledTime;
    private String actualPredictedTime;

    public String getDestination() { return destination; }
    public int getDueIn() { return dueIn; }
    public String getLine() { return line; }

    public int getLineColour(Context context) {
        if (context == null) {
            Log.e("MetroApp", "Context is NULL! Returning default color.");
            return Color.BLACK; // Default fallback
        }
        switch (line.toLowerCase()){
            case "green": return ContextCompat.getColor(context, R.color.green_line);
            case "yellow" : return ContextCompat.getColor(context, R.color.yellow_line);
            default: return ContextCompat.getColor(context, R.color.red_line);
        }
    }
    public String getActualScheduledTime() { return actualScheduledTime; }
    public String getActualPredictedTime() { return actualPredictedTime; }
}
