package com.bearm.glyndex;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        CategoryRepository categoryRepository = new CategoryRepository(getApplication());
        List<Category> categoryList = categoryRepository.getCategoryList();

        //List<Category> categoryList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new CategoryAdapter(this, categoryList);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    private List<Category> getExampleCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        Category cat1 = new Category();
        cat1.setId(1);
        cat1.setName("Lácteos");
        cat1.setIconName("ic_milk");
        Category cat2 = new Category();
        cat2.setId(2);
        cat2.setName("Harina y cereales");
        cat2.setIconName("ic_bread");
        Category cat3 = new Category();
        cat3.setId(3);
        cat3.setName("Legumbres");
        cat3.setIconName("ic_greenbeans");
        categoryList.add(cat1);
        categoryList.add(cat2);
        categoryList.add(cat3);
        return categoryList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}