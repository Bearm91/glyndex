package com.bearm.glyndex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.helpers.*;
import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.DetailsAdapter;
import com.bearm.glyndex.models.*;
import com.bearm.glyndex.repositories.*;

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
        String grams = gramsPerChRation + getString(R.string.grams);
        tvFoodIg.setText(grams);
    }

    private void loadIG(Integer gi) {
        TextView tvFoodIg = findViewById(R.id.tv_ig);
        if (gi != null) {
            tvFoodIg.setText(String.valueOf(gi));
        } else {
            tvFoodIg.setText(R.string.dash_symbol);
        }

        ImageView ivIGIcon = findViewById(R.id.iv_ig_icon);
        String iconName = DetailsHelper.getIGIconName(gi);

        int resourceIdImage = getResources().getIdentifier(iconName, "drawable",
                getPackageName());
        //use this id to set the image anywhere
        ivIGIcon.setImageResource(resourceIdImage);
    }

    private void loadCategory(Category category) {
        TextView tvCategoryName = findViewById(R.id.tv_category_name);
        tvCategoryName.setText(category.getName());

        ImageView categoryIcon = findViewById(R.id.iv_cat_icon);
        String iconName = category.getIconName();
        if (iconName != null) {
            int resourceIdImage = getResources().getIdentifier(iconName, "drawable",
                    getPackageName());
            //use this id to set the image anywhere
            categoryIcon.setImageResource(resourceIdImage);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_GI) {
            goToInfoScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToInfoScreen(){
        Intent infoIntent = new Intent(this, InfoActivity.class);
        startActivity(infoIntent);
    }
}