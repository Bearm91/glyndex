package com.bearm.glyndex.helpers;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.bearm.glyndex.R;
import com.google.android.material.textfield.TextInputEditText;

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

    //Verify that the fields of the form are not empty
    public static boolean verifyFields(Context context, TextInputEditText measurementNameInput, TextInputEditText measurementQuantityInput) {
        boolean valid = false;
        String name = String.valueOf(measurementNameInput.getText());
        String quantity = String.valueOf(measurementQuantityInput.getText());
        Log.i("ADD ITEM", "Name: " + name + ", Quantity: " + quantity);

        if (name.isEmpty()) {
            measurementNameInput.setError(context.getString(R.string.error_empty_name));
            measurementNameInput.requestFocus();
        } else {
            measurementNameInput.setError(null);
        }
        if (quantity.isEmpty()) {
            measurementQuantityInput.setError(context.getString(R.string.error_empty_name));
            measurementQuantityInput.requestFocus();
        } else if (!isNumber(quantity)) {
            measurementQuantityInput.setError(context.getString(R.string.not_valid_field_error));
            measurementQuantityInput.requestFocus();
        } else {
            measurementQuantityInput.setError(null);
        }

        if ((!name.isEmpty()) && (!quantity.isEmpty()) && isNumber(quantity)) {
            valid = true;
        }
        return valid;
    }

    public static boolean isNumber(String chQuantity) {
        try {
            Float.parseFloat(chQuantity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
