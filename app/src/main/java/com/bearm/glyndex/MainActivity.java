package com.bearm.glyndex;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.bearm.glyndex.repositories.CategoryRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoryAdapter adapter;
    List<Category> categoryList;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView) findViewById(R.id.rv);
        loadCategoryList();

        FloatingActionButton fabSearch = findViewById(R.id.fab_search);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFoodScreen(0, true);
            }
        });

    }

    //Adds category items to the main list
    private void loadCategoryList() {
        categoryList = getCategoryList();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new CategoryAdapter(this, categoryList, new CategoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goToFoodScreen(position, false);
            }
        });

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    //Gets all category items from database
    private List<Category> getCategoryList() {
        CategoryRepository categoryRepository = new CategoryRepository(getApplication());
        return categoryRepository.getCategoryList();
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
            goToInfoScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Shows a dialog with info about the app */
    private void showAboutDialog() {
        View view = getLayoutInflater().inflate(R.layout.about_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle(R.string.about_title)
                .setIcon(R.mipmap.ic_launcher_pyramid_round)
                .setCancelable(true)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close dialog.
                    }
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
    private void goToInfoScreen() {
        Intent infoIntent = new Intent(this, InfoActivity.class);
        startActivity(infoIntent);
    }


}