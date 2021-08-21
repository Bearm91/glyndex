package com.bearm.glyndex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.activities.FoodActivity;
import com.bearm.glyndex.activities.InfoActivity;
import com.bearm.glyndex.adapters.CategoryAdapter;
import com.bearm.glyndex.helpers.Constants;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.viewModels.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoryAdapter adapter;
    List<Category> categoryList;
    RecyclerView rv;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_ONBOARDING_KEY, MODE_PRIVATE);

        rv = findViewById(R.id.rv);
        loadCategoryList();

        FloatingActionButton fabSearch = findViewById(R.id.fab_search);
        fabSearch.setOnClickListener(v -> goToFoodScreen(0, true));
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean showOnboardingScreen = sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_ONBOARDING_SHOWINFO, true);
        if (showOnboardingScreen) {
            goToInfoScreen(Boolean.TRUE);
        }
    }

    //Adds category items to the main list
    private void loadCategoryList() {
        categoryList = getCategoryList();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new CategoryAdapter(this, categoryList, (view, position) -> goToFoodScreen(position, false));

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    //Gets all category items from database
    private List<Category> getCategoryList() {
        CategoryViewModel categoryViewModel = new CategoryViewModel(getApplication());
        return categoryViewModel.getCategoryList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Info about the app
        if (id == R.id.action_about) {
            showAboutDialog();
            return true;
        }

        //Info about glycemic index
        if (id == R.id.action_GI) {
            goToInfoScreen(Boolean.FALSE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Shows a dialog with info about the app */
    private void showAboutDialog() {
        View view = getLayoutInflater().inflate(R.layout.about_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myAbout);
        builder.setView(view);
        builder.setTitle(R.string.about_title)
                .setIcon(R.mipmap.ic_launcher_pyramid_round)
                .setCancelable(true)
                .setPositiveButton(R.string.close_dialog_button, (dialog, which) -> {
                    //Close dialog.
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Opens food screen with list of food in selected category.
     * Sends categoryId, category name and boolean indicating whether screen was opened from search button
     */
    private void goToFoodScreen(int position, boolean search) {
        Category cat = categoryList.get(position);
        Intent foodIntent = new Intent(this, FoodActivity.class);
        foodIntent.putExtra(Constants.CATEGORY_ID_FIELD, cat.getId());
        foodIntent.putExtra(Constants.CATEGORY_NAME_FIELD, cat.getName());
        foodIntent.putExtra(Constants.SEARCH_BOOLEAN, search);
        startActivity(foodIntent);
    }

    /** Opens info screen with info about the glycemic index */
    private void goToInfoScreen(boolean isOnBoarding) {
        Intent infoIntent = new Intent(this, InfoActivity.class);
        infoIntent.putExtra(Constants.SHARED_PREFERENCES_ONBOARDING_KEY, isOnBoarding);
        startActivity(infoIntent);
    }
}