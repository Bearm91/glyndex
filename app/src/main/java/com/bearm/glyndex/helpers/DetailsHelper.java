package com.bearm.glyndex.helpers;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.bearm.glyndex.R;

public class DetailsHelper {

    public static int getIGColor(Context context, Integer currentFoodGI) {
        int colorId = ContextCompat.getColor(context, R.color.colorYellow);

        if (currentFoodGI == null) {
            colorId = ContextCompat.getColor(context, R.color.colorGray);
        } else if (currentFoodGI <= 55) {
            colorId = ContextCompat.getColor(context, R.color.colorGreen);
        } else if (currentFoodGI >= 70) {
            colorId = ContextCompat.getColor(context, R.color.colorRed);
        }
        return colorId;
    }
}
