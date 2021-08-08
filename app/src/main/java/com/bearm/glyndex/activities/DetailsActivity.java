package com.bearm.glyndex.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.DetailsAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.helpers.DetailsHelper;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;
import com.bearm.glyndex.repositories.CategoryRepository;
import com.bearm.glyndex.repositories.FoodRepository;
import com.bearm.glyndex.repositories.MeasurementRepository;
import com.bearm.glyndex.viewModels.MeasurementViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    List<Measurement> measurementList;
    CardView measurementTable;
    int categoryId;
    String categoryName;
    MeasurementRepository measurementRepository;
    int foodId;
    MeasurementViewModel measurementViewModel;
    DetailsAdapter detailsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);

        Bundle bundle = getIntent().getExtras();
        foodId = 0;
        if (bundle != null) {
            foodId = bundle.getInt(Constants.FOOD_ID_FIELD);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        measurementRepository = new MeasurementRepository(getApplication());

        loadDetailsInfo(getFoodInfo(foodId));
        loadFoodDetails(foodId);


        Button addBtn = findViewById(R.id.btn_add_measurement);
        addBtn.setOnClickListener((View v) -> showAddMeasurementDialog(true, null));
    }


    private Food getFoodInfo(int foodId) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        return foodRepository.getFoodById(foodId);
    }

    private void loadFoodDetails(int foodId) {
        measurementTable = findViewById(R.id.cv_measr_table);

        RecyclerView rv = findViewById(R.id.rv);

        measurementList = getMeasurementList(foodId);

        measurementList = new ArrayList<>();
        detailsAdapter = new DetailsAdapter(getApplicationContext(), measurementList, measurementViewModel);

        RecyclerView rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(detailsAdapter);

        ViewModelProvider.AndroidViewModelFactory myViewModelProviderFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        measurementViewModel = new ViewModelProvider(this, myViewModelProviderFactory).get(MeasurementViewModel.class);
        measurementViewModel.getMeasurementByFood(foodId).observe(this, measurements -> detailsAdapter.setEvents(measurements)
        );
        );
    }

    private List<Measurement> getMeasurementList(int foodId) {
        return measurementRepository.getMeasurementByFood(foodId);
    }

    private void loadDetailsInfo(Food food) {
        loadFoodName(food.getName());
        Category currentCategory = getCategoryInfo(food.getId());
        loadCategory(currentCategory);
        loadIG(food.getGI());
        loadCHRationG(food.getGramsPerChRation());

    }

    private Category getCategoryInfo(int id) {
        CategoryRepository categoryRepository = new CategoryRepository(getApplication());
        return categoryRepository.getById(id);
    }

    private void loadCHRationG(long gramsPerChRation) {
        TextView tvFoodIg = findViewById(R.id.tv_carbs_g);
        String grams = getString(R.string.grams, String.valueOf(gramsPerChRation));
        tvFoodIg.setText(grams);
    }

    private void loadIG(Integer gi) {
        TextView tvFoodIg = findViewById(R.id.tv_ig);

        FitChart fitChart = findViewById(R.id.fc_gi_chart);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(100f);

        if (gi != null) {
            tvFoodIg.setText(String.valueOf(gi));
            fitChart.setValues(getFitChartValues(gi));
        } else {
            tvFoodIg.setText(R.string.dash_symbol);
        }
    }

    private List<FitChartValue> getFitChartValues(Integer gi){
        List<FitChartValue> values = new ArrayList<>();
        FitChartValue fitChartValue = new FitChartValue((float) gi, DetailsHelper.getIGColor(getApplicationContext(), gi));
        values.add(fitChartValue);
        return values;
    }



    private void loadCategory(Category category) {
        TextView tvCategoryName = findViewById(R.id.tv_category_name);
        tvCategoryName.setText(category.getName());
    }

    private void loadFoodName(String name) {
        TextView tvFoodName = findViewById(R.id.tv_food_name);
        tvFoodName.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        intent.putExtra(Constants.CATEGORY_NAME_FIELD, categoryName);
        setResult(Constants.RESULT_CODE_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showAddMeasurementDialog(boolean isNew, Measurement measurement) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.add_measurement_dialog, null);

        final TextInputEditText measurementNameInput = view.findViewById(R.id.edit_measurement_name);
        final TextInputEditText measurementQuantityInput = view.findViewById(R.id.edit_measurement_quantity);
        final Button cancelButton = view.findViewById(R.id.btn_cancel);
        final Button saveButton = view.findViewById(R.id.btn_save);

        if (!isNew) {
            measurementNameInput.setText(measurement.getName());
            measurementQuantityInput.setText(String.valueOf(measurement.getChRationPerMeasurement() * Constants.GRAMS_IN_CHRATION));
        }

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        saveButton.setOnClickListener((View v) -> {
            if(verifyFields(measurementNameInput, measurementQuantityInput)){
                float measurementQuantity = Float.parseFloat(String.valueOf(measurementQuantityInput.getText()));
                String measurementName = String.valueOf(measurementNameInput.getText());
                float chRation = measurementQuantity / Constants.GRAMS_IN_CHRATION;
                if (isNew) {
                    Measurement newMeasurement = new Measurement(measurementName, chRation, foodId);
                    addMeasurement(newMeasurement);
                } else {
                    updateMeasurement(measurement, measurementName, chRation);
                }
                alertDialog.cancel();
            }
        });

        cancelButton.setOnClickListener((View v) -> alertDialog.cancel());

        alertDialog.show();
    }

    //Verify that the fields of the form are not empty
    private boolean verifyFields(TextInputEditText measurementNameInput, TextInputEditText measurementQuantityInput) {
        boolean valid = false;
        String name = String.valueOf(measurementNameInput.getText());
        String quantity = String.valueOf(measurementQuantityInput.getText());
        Log.i("ADD MEASUREMENT", "Name: " + name + ", Quantity: " + quantity);

        if (name.isEmpty()) {
            measurementNameInput.setError(getString(R.string.error_empty_name));
            measurementNameInput.requestFocus();
        } else {
            measurementNameInput.setError(null);
        }
        if (quantity.isEmpty()) {
            measurementQuantityInput.setError(getString(R.string.error_empty_name));
            measurementQuantityInput.requestFocus();
        } else if (!isNumber(quantity)) {
            measurementQuantityInput.setError(getString(R.string.not_valid_field_error));
            measurementQuantityInput.requestFocus();
        } else {
            measurementQuantityInput.setError(null);
        }

        if ((!name.isEmpty()) && (!quantity.isEmpty()) && isNumber(quantity)) {
            valid = true;
            return true;
        }
        return valid;
    }

    private boolean isNumber(String chQuantity) {
        try {
            Float.parseFloat(chQuantity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void addMeasurement(Measurement measurement) {
        if (measurement != null) {
        float chRation = measurementQuantity / 10;
        Log.i("ADD MEASUREMENT METHOD", "new Measurement (R): " + chRation);
        Measurement measurement = new Measurement(measurementName, chRation, foodId);
            measurementRepository.insertMeasurement(measurement);
        }
    }