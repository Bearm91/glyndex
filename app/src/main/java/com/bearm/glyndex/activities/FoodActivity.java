package com.bearm.glyndex.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.viewModels.CategoryViewModel;
import com.bearm.glyndex.viewModels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    List<Food> foodList;

    String categoryName;
    int categoryId;
    RecyclerView.LayoutManager layoutManager;
    FoodViewModel foodViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryId = bundle.getInt(Constants.CATEGORY_ID_FIELD);
            categoryName = getCategoryById(categoryId).getName();
        }
        getSupportActionBar().setTitle(categoryName);

        layoutManager = new LinearLayoutManager(this);
        loadFoodList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_menu) {
            openFoodForm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFoodForm() {
        Intent foodFormIntent = new Intent(this, FoodFormActivity.class);
        foodFormIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodFormIntent.putExtra(Constants.FOOD_FORM_MODE, Constants.FOOD_FORM_CREATE_MODE);
        startActivityForResult(foodFormIntent, 1);

    }


    private void loadFoodList() {
        RecyclerView rv = findViewById(R.id.rv);
        ViewModelProvider.AndroidViewModelFactory myViewModelProviderFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        foodViewModel = new ViewModelProvider(this, myViewModelProviderFactory).get(FoodViewModel.class);

        foodList = new ArrayList<>();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList, (view, position) -> goToFoodDetailsScreen(position));

        foodViewModel.getFoodByCategory(categoryId).observe(this, adapter::setEvents);

        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }



    private List<Food> getFoodList(int categoryId) {
        return foodViewModel.getFoodListByCategory(categoryId);
    }

    private void goToFoodDetailsScreen(int position) {
        Food food = getFoodList(categoryId).get(position);
        Log.e("Food", String.valueOf(food));
        Intent foodDetailsIntent = new Intent(this, DetailsActivity.class);
        foodDetailsIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodDetailsIntent.putExtra(Constants.FOOD_ID_FIELD, food.getId());
        startActivityForResult(foodDetailsIntent, 1);
    }

    @Deprecated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_OK && resultCode == Constants.RESULT_CODE_OK) {
            categoryId = data.getIntExtra(Constants.CATEGORY_ID_FIELD, 0);
            categoryName = data.getStringExtra(Constants.CATEGORY_NAME_FIELD);
        }
    }

    private Category getCategoryById(Integer categoryId){
        CategoryViewModel categoryViewModel = new CategoryViewModel(getApplication());
        return categoryViewModel.getById(categoryId);
    }
}