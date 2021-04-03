package com.bearm.glyndex.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    List<Measurement> measurementList;
    CardView measurementTable;
    int categoryId;
    String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);

        Bundle bundle = getIntent().getExtras();
        int foodId = 0;
        if (bundle != null) {
            foodId = bundle.getInt(Constants.FOOD_ID_FIELD);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


        loadDetailsInfo(getFoodInfo(foodId));
        loadFoodDetails(foodId);

    }

    private Food getFoodInfo(int foodId) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        return foodRepository.getFoodById(foodId);
    }

    private void loadFoodDetails(int foodId) {
        measurementTable = findViewById(R.id.cv_measr_table);

        RecyclerView rv = findViewById(R.id.rv);

        measurementList = getMeasurementList(foodId);

        if (!measurementList.isEmpty()) {
            measurementTable.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            DetailsAdapter detailsAdapter = new DetailsAdapter(getApplicationContext(), measurementList);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(detailsAdapter);
        } else {
            measurementTable.setVisibility(View.INVISIBLE);
        }
    }

    private List<Measurement> getMeasurementList(int foodId) {
        MeasurementRepository measurementRepository = new MeasurementRepository(getApplication());
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
}