package com.bearm.glyndex.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;
import com.bearm.glyndex.repositories.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    FoodRepository foodRepository;

    public FoodViewModel(@NonNull Application application) {
        super(application);

        foodRepository = new FoodRepository(application);
    }

    public List<Food> getFoodByCategory(Integer categoryId) {
        return foodRepository.getFoodByCategory(categoryId);
    }

    public List<Food> getAllFoodList() {
        return foodRepository.getAllFoodList();
    }

    public List<Food> getFoodByName(String foodName) {
        return foodRepository.getFoodByName(foodName);
    }
}
