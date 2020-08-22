package com.bearm.glyndex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.repositories.FoodRepository;

import java.util.List;

public class FoodActivity extends AppCompatActivity {

    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Bundle bundle = getIntent().getExtras();
        int categoryId = bundle.getInt("CategoryId");
        String categoryName = bundle.getString("CategoryName");
        loadFoodList(categoryId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(categoryName);
    }

    private void loadFoodList(int catId) {
        RecyclerView rv = findViewById(R.id.rv);

        foodList = getFoodList(catId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList, new FoodAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goToFoodDetailsScreen(position);
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private List<Food> getFoodList(int categoryId) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        List<Food> foodList = foodRepository.getFoodByCategory(categoryId);
        Log.e("FOODLIST", String.valueOf(foodList.size()));
        return foodList;
    }

    private void goToFoodDetailsScreen(int position) {
        Food food = foodList.get(position);
        Log.e("Food", String.valueOf(food.toString()));
        /*Intent foodDetailsIntent = new Intent(this, DetailsActivity.class);
        foodDetailsIntent.putExtra("FoodId", food.getId());
        foodDetailsIntent.putExtra("FoodName", food.getName());
        startActivity(foodDetailsIntent);*/
    }
}