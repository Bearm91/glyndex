package com.bearm.glyndex.activities;

import static com.bearm.glyndex.helpers.DetailsHelper.isNumber;
import static com.bearm.glyndex.helpers.DetailsHelper.verifyFields;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bearm.glyndex.R;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.viewModels.FoodViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class FoodFormActivity extends AppCompatActivity {

    String categoryName;
    int categoryId;
    int foodId = 0;
    String formMode;
    Food food;

    FoodViewModel foodViewModel;

    TextInputEditText foodNameInput;
    TextInputEditText foodchgramsInput;
    TextInputEditText foodGiInput;
    TextView foodFormTitle;
    CheckBox checkBox;
    Button cancelButton;
    Button saveButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryId = bundle.getInt(Constants.CATEGORY_ID_FIELD);
            categoryName = bundle.getString(Constants.CATEGORY_NAME_FIELD);
            foodId = bundle.getInt(Constants.FOOD_ID_FIELD);
            formMode = bundle.getString(Constants.FOOD_FORM_MODE);
        }

        foodViewModel = new FoodViewModel(getApplication());
        loadFormItems();
    }

    private void loadFormItems(){
        foodFormTitle = findViewById(R.id.food_form_title_add);
        foodNameInput = findViewById(R.id.edit_food_name);
        foodchgramsInput = findViewById(R.id.edit_food_chgrams);
        foodGiInput = findViewById(R.id.edit_food_gi);
        checkBox = findViewById(R.id.checkbox_unknown_gi);
        cancelButton = findViewById(R.id.btn_cancel);
        saveButton = findViewById(R.id.btn_save);

        if (formMode.equals(Constants.FOOD_FORM_EDIT_MODE) && foodId > 0) {
            setFoodInfo();
        }

        saveButton.setOnClickListener(view -> {
            try {
                Food newFood = getNewFood();
                if (newFood != null) {
                    saveFood(newFood);
                }
                goToFoodScreen();
            } catch (SQLiteConstraintException e) {
                Log.e("saveFood ERROR", e.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.food_form_save_error), Toast.LENGTH_LONG).show();
            }
        });

        cancelButton.setOnClickListener(view -> onBackPressed());
    }

    private void goToFoodScreen() {
        Intent foodIntent = new Intent(this, FoodActivity.class);
        foodIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodIntent.putExtra(Constants.CATEGORY_NAME_FIELD, categoryName);
        startActivity(foodIntent);
    }


    private void setFoodInfo() {
        if(formMode.equals(Constants.FOOD_FORM_EDIT_MODE)) {
            foodFormTitle.setText(getString(R.string.edit_food_dialog_title));
        } else {
            foodFormTitle.setText(getString(R.string.add_food_dialog_title));
        }
        food = foodViewModel.getFoodById(foodId);
        foodNameInput.setText(food.getName());
        foodchgramsInput.setText(String.valueOf(food.getGramsPerChRation()));
        foodGiInput.setText(food.getGI() != null ? String.valueOf(food.getGI()) : "-");
        checkBox.setChecked(food.getGI() == null);
    }

    private Food getNewFood() {
        if(verifyFields(getApplicationContext(), foodNameInput, foodchgramsInput)) {
            Integer foodGi = null;
            long foodChgrams = Long.parseLong(String.valueOf(foodchgramsInput.getText()));
            String foodName = String.valueOf(foodNameInput.getText());
            if (!checkBox.isChecked() && isNumber(String.valueOf(foodGiInput.getText()))) {
                foodGi = Integer.parseInt(String.valueOf(foodGiInput.getText()));
            }

            if(food == null) {
                food = new Food();
            }
            food.setCategory(categoryId);
            food.setName(foodName);
            food.setGramsPerChRation(foodChgrams);
            food.setGI(foodGi);
            food.setCustom(Boolean.TRUE);
        }
        return food;
    }


    private void saveFood(Food food) throws SQLiteConstraintException{
        if (formMode.equals(Constants.FOOD_FORM_CREATE_MODE)) {
            foodViewModel.insertFood(food);
        } else if (formMode.equals(Constants.FOOD_FORM_EDIT_MODE)) {
            foodViewModel.updateFood(food);
        }
        Toast.makeText(getApplicationContext(), getString(R.string.food_form_save_success_message), Toast.LENGTH_LONG).show();

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