package com.bearm.glyndex.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.repositories.FoodRepository;

import java.util.List;

public class FoodActivity extends AppCompatActivity {

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

        FoodRepository foodRepository = new FoodRepository(getApplication());
        List<Food> foodList = foodRepository.getFoodByCategory(catId);
        Log.e("FOODLIST", String.valueOf(foodList.size()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}