package com.bearm.glyndex.repositories;


import android.app.Application;

import com.bearm.glyndex.AppDatabase;
import com.bearm.glyndex.DAO.CategoryDao;
import com.bearm.glyndex.models.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private List<Category> categoryList;
    private int categoryId;

    public CategoryRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());

        categoryDao = db.categoryDao();
        categoryList = categoryDao.findAll();

    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}

