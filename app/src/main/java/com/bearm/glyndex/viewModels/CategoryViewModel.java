package com.bearm.glyndex.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private List<Category> categoryList;
    CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        categoryRepository = new CategoryRepository(application);

        categoryList = categoryRepository.getCategoryList();
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public Category getById(int id) {
        return categoryRepository.getById(id);
    }
}
