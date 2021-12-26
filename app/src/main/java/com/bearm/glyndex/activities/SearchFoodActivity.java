package com.bearm.glyndex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.viewModels.CategoryViewModel;
import com.bearm.glyndex.viewModels.FoodViewModel;

import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        getSupportActionBar().setTitle(R.string.search_title);
        SearchView searchView = findViewById(R.id.sv_food);

        searchView.setVisibility(View.VISIBLE);
        searchView.setQueryHint(getString(R.string.searchView_hint));
        loadSearchFoodList(null);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadSearchFoodList(newText);
                return false;
            }
        });
    }

    private void loadSearchFoodList(String filter) {
        RecyclerView rv = findViewById(R.id.rv);
        List<Food> foodList = getFoodList(filter);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList, (view, position) -> goToFoodDetailsScreen(foodList.get(position)));

        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }

    private List<Food> getFoodList(String filter) {
        FoodViewModel foodViewModel = new FoodViewModel(getApplication());
        if ((filter != null) && (!filter.equals(""))) {
            return foodViewModel.getFoodByName('%' + filter + '%');
        } else {
            return foodViewModel.getAllFoodList();
        }
    }

    private void goToFoodDetailsScreen(Food food) {
        int categoryId = food.getCategory();
        Log.e("Food", String.valueOf(food));
        Intent foodDetailsIntent = new Intent(this, DetailsActivity.class);
        foodDetailsIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodDetailsIntent.putExtra(Constants.CATEGORY_NAME_FIELD, getCategoryById(categoryId).getName());
        foodDetailsIntent.putExtra(Constants.FOOD_ID_FIELD, food.getId());
        startActivityForResult(foodDetailsIntent, 1);
    }

    private Category getCategoryById(Integer categoryId){
        CategoryViewModel categoryViewModel = new CategoryViewModel(getApplication());
        return categoryViewModel.getById(categoryId);
    }
}
