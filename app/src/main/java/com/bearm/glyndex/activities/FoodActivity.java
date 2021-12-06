package com.bearm.glyndex.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.FoodAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.viewModels.FoodViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import static com.bearm.glyndex.helpers.DetailsHelper.isNumber;
import static com.bearm.glyndex.helpers.DetailsHelper.verifyFields;

public class FoodActivity extends AppCompatActivity {

    List<Food> foodList;

    String categoryName;
    int categoryId;
    boolean search;
    RecyclerView rv;
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
            categoryName = bundle.getString(Constants.CATEGORY_NAME_FIELD);
            search = bundle.getBoolean(Constants.SEARCH_BOOLEAN);
        }

        layoutManager = new LinearLayoutManager(this);

        SearchView searchView = findViewById(R.id.sv_food);
        if (search) {
            getSupportActionBar().setTitle(R.string.search_title);
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
                    loadSearchFoodList(categoryId, newText);
                    return false;
                }
            });
        } else {
            getSupportActionBar().setTitle(categoryName);
            searchView.setVisibility(View.GONE);
        }

        loadFoodList(categoryId);
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
            showAddFoodDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddFoodDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.add_food_dialog, null);

        final TextInputEditText foodNameInput = view.findViewById(R.id.edit_food_name);
        final TextInputEditText foodchgramsInput = view.findViewById(R.id.edit_food_chgrams);
        final TextInputEditText foodGiInput = view.findViewById(R.id.edit_food_gi);
        final CheckBox checkBox = view.findViewById(R.id.checkbox_unknown_gi);

        final Button cancelButton = view.findViewById(R.id.btn_cancel);
        final Button saveButton = view.findViewById(R.id.btn_save);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        saveButton.setOnClickListener((View v) -> {
            if(verifyFields(getApplicationContext(), foodNameInput, foodchgramsInput)){
                Integer foodGi = null;
                long foodChgrams = Long.parseLong(String.valueOf(foodchgramsInput.getText()));
                String foodName = String.valueOf(foodNameInput.getText());
                if (!checkBox.isChecked() && isNumber(String.valueOf(foodGiInput.getText()))) {
                    foodGi = Integer.parseInt(String.valueOf(foodGiInput.getText()));
                }
                Food newFood = new Food();
                newFood.setCategory(categoryId);
                newFood.setName(foodName);
                newFood.setGramsPerChRation(foodChgrams);
                newFood.setGI(foodGi);
                newFood.setCustom(Boolean.TRUE);
                addFood(newFood);

                alertDialog.cancel();
            }
        });

        cancelButton.setOnClickListener((View v) -> alertDialog.cancel());

        alertDialog.show();
    }

    private void addFood(Food food) {
        if (food != null) {
            foodViewModel.insertFood(food);
        }
    }

    private void loadFoodList(int catId) {
        rv = findViewById(R.id.rv);
        ViewModelProvider.AndroidViewModelFactory myViewModelProviderFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        foodViewModel = new ViewModelProvider(this, myViewModelProviderFactory).get(FoodViewModel.class);

        foodList = new ArrayList<>();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList, (view, position) -> goToFoodDetailsScreen(position));

        foodViewModel.getFoodByCategory(categoryId).observe(this, adapter::setEvents);

        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }

    private void loadSearchFoodList(int catId, String filter) {
        rv = findViewById(R.id.rv);

        foodViewModel = new FoodViewModel(getApplication());

        if (filter != null){
            foodList = getFoodList(catId, filter);
        }
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FoodAdapter adapter = new FoodAdapter(this, foodList, (view, position) -> goToFoodDetailsScreen(position));

        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }

    private List<Food> getFoodList(int categoryId, String filter) {
        List<Food> newFoodList;
        if ((filter != null) && (!filter.equals(""))) {
            newFoodList = foodViewModel.getFoodByName('%' + filter + '%');
        } else {
            if (categoryId > 0) {
                newFoodList = foodViewModel.getFoodListByCategory(categoryId);
            } else {
                newFoodList = foodViewModel.getAllFoodList();
            }
        }
        return newFoodList;
    }

    private void goToFoodDetailsScreen(int position) {
        Food food = getFoodList(categoryId, null).get(position);
        Log.e("Food", String.valueOf(food));
        Intent foodDetailsIntent = new Intent(this, DetailsActivity.class);
        foodDetailsIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodDetailsIntent.putExtra(Constants.CATEGORY_NAME_FIELD, categoryName);
        foodDetailsIntent.putExtra(Constants.FOOD_ID_FIELD, food.getId());
        startActivityForResult(foodDetailsIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_OK && resultCode == Constants.RESULT_CODE_OK) {
            categoryId = data.getIntExtra(Constants.CATEGORY_ID_FIELD, 0);
            categoryName = data.getStringExtra(Constants.CATEGORY_NAME_FIELD);
        }
    }
}