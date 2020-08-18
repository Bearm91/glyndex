package com.bearm.glyndex.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRespository;
    private List<Category> categoryList;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        categoryRespository = new CategoryRepository(application);

        categoryList = categoryRespository.getCategoryList();
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

}
