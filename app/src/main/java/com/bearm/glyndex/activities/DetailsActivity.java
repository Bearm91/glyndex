package com.bearm.glyndex.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.R;
import com.bearm.glyndex.adapters.DetailsAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.helpers.DetailsHelper;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;
import com.bearm.glyndex.repositories.FoodRepository;
import com.bearm.glyndex.viewModels.CategoryViewModel;
import com.bearm.glyndex.viewModels.FoodViewModel;
import com.bearm.glyndex.viewModels.MeasurementViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bearm.glyndex.helpers.DetailsHelper.verifyFields;

public class DetailsActivity extends AppCompatActivity {

    List<Measurement> measurementList;
    CardView measurementTable;
    int categoryId;
    int foodId;
    MeasurementViewModel measurementViewModel;
    DetailsAdapter detailsAdapter;
    FoodViewModel foodViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);

        Bundle bundle = getIntent().getExtras();
        foodId = 0;
        if (bundle != null) {
            foodId = bundle.getInt(Constants.FOOD_ID_FIELD);
            categoryId = bundle.getInt(Constants.CATEGORY_ID_FIELD);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));

        measurementViewModel = new MeasurementViewModel(getApplication());

        loadDetailsInfo(getFoodInfo(foodId));
        loadFoodDetails(foodId);


        Button addBtn = findViewById(R.id.btn_add_measurement);
        addBtn.setOnClickListener((View v) -> showAddMeasurementDialog());
    }


    private Food getFoodInfo(int foodId) {
        FoodRepository foodRepository = new FoodRepository(getApplication());
        return foodRepository.getFoodById(foodId);
    }

    private void loadFoodDetails(int foodId) {
        measurementTable = findViewById(R.id.cv_measr_table);

        measurementList = new ArrayList<>();
        detailsAdapter = new DetailsAdapter(getApplicationContext(), measurementList, measurementViewModel);

        RecyclerView rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(detailsAdapter);

        ViewModelProvider.AndroidViewModelFactory myViewModelProviderFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        measurementViewModel = new ViewModelProvider(this, myViewModelProviderFactory).get(MeasurementViewModel.class);
        measurementViewModel.getMeasurementByFood(foodId).observe(this, measurements -> detailsAdapter.setEvents(measurements));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                Measurement measurement = detailsAdapter.getItem(position);
                measurementViewModel.deleteMeasurement(measurement);
                measurementList = detailsAdapter.getMeasurementList();
                measurementList.remove(position);
                detailsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), getString(R.string.toast_measurement_removed), Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }

    private void loadDetailsInfo(Food food) {
        loadFoodName(food.getName());
        Category currentCategory = getCategoryInfo(food.getId());
        loadCategory(currentCategory);
        loadIG(food.getGI());
        loadCHRationG(food.getGramsPerChRation());

    }

    private Category getCategoryInfo(int id) {
        CategoryViewModel categoryViewModel = new CategoryViewModel(getApplication());
        return categoryViewModel.getByFoodId(id);
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
        setResult(Constants.RESULT_CODE_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showAddMeasurementDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.add_measurement_dialog, null);

        final TextInputEditText measurementNameInput = view.findViewById(R.id.edit_measurement_name);
        final TextInputEditText measurementQuantityInput = view.findViewById(R.id.edit_measurement_quantity);
        final Button cancelButton = view.findViewById(R.id.btn_cancel);
        final Button saveButton = view.findViewById(R.id.btn_save);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        saveButton.setOnClickListener((View v) -> {
            if(verifyFields(getApplicationContext(), measurementNameInput, measurementQuantityInput)){
                float measurementQuantity = Float.parseFloat(String.valueOf(measurementQuantityInput.getText()));
                String measurementName = String.valueOf(measurementNameInput.getText());
                float chRation = measurementQuantity / Constants.GRAMS_IN_CHRATION;
                Measurement newMeasurement = new Measurement(measurementName, chRation, foodId);
                addMeasurement(newMeasurement);

                alertDialog.cancel();
            }
        });

        cancelButton.setOnClickListener((View v) -> alertDialog.cancel());

        alertDialog.show();
    }

    private void addMeasurement(Measurement measurement) {
        if (measurement != null) {
            measurementViewModel.insertMeasurement(measurement);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_menu) {
            goToEditFoodForm();
            return true;
        }

        if (id == R.id.action_delete_menu) {
            deleteFood(foodId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteFood(int foodId) {
        foodViewModel = new FoodViewModel(getApplication());
        foodViewModel.deleteFood(foodId);
        onBackPressed();
        Toast.makeText(getApplicationContext(), getString(R.string.food_deleted_success_message), Toast.LENGTH_LONG).show();

    }



    private void goToEditFoodForm() {
        Intent foodFormIntent = new Intent(this, FoodFormActivity.class);
        foodFormIntent.putExtra(Constants.CATEGORY_ID_FIELD, categoryId);
        foodFormIntent.putExtra(Constants.FOOD_ID_FIELD, foodId);
        foodFormIntent.putExtra(Constants.FOOD_FORM_MODE, Constants.FOOD_FORM_EDIT_MODE);
        startActivityForResult(foodFormIntent, 1);
    }

}