package com.bearm.glyndex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.repositories.FoodRepository;

import java.util.List;

public class FoodActivity extends AppCompatActivity {

    List<Food> foodList;

    String categoryName;
    int categoryId;
    boolean search;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryId = bundle.getInt("CategoryId");
            categoryName = bundle.getString("CategoryName");
            search = bundle.getBoolean("IsSearch");
        }

        layoutManager = new LinearLayoutManager(this);

        SearchView searchView = findViewById(R.id.sv_food);
        if(search) {
            getSupportActionBar().setTitle("Buscar");
            searchView.setVisibility(View.VISIBLE);
            searchView.setQueryHint(getString(R.string.searchView_hint));
            categoryId = 0;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    loadFoodList(categoryId, newText);
                    return false;
                }
            });
        } else {
            getSupportActionBar().setTitle(categoryName);
            searchView.setVisibility(View.GONE);
        }

        loadFoodList(categoryId, null);
    }

    private void loadFoodList(int catId, String filter) {
        rv = findViewById(R.id.rv);

        foodList = getFoodList(catId, filter);
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

    private List<Food> getFoodList(int categoryId, String filter) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        List<Food> foodList;
        if ((filter != null) && (!filter.equals(""))){
            foodList = foodRepository.getFoodByName('%'+filter+'%');
        } else {
            if (categoryId > 0) {
                foodList = foodRepository.getFoodByCategory(categoryId);
            } else {
                foodList = foodRepository.getAllFoodList();
            }
        }
        Log.e("FOODLIST", String.valueOf(foodList.size()));
        return foodList;
    }

    private void goToFoodDetailsScreen(int position) {
        Food food = foodList.get(position);
        Log.e("Food", String.valueOf(food.toString()));
        Intent foodDetailsIntent = new Intent(this, DetailsActivity.class);
        foodDetailsIntent.putExtra("CategoryId", categoryId);
        foodDetailsIntent.putExtra("CategoryName", categoryName);
        foodDetailsIntent.putExtra("FoodId", food.getId());
        startActivityForResult(foodDetailsIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                categoryId = data.getIntExtra("CategoryId", 0);
                categoryName = data.getStringExtra("CategoryName");
            }
        }
    }
}