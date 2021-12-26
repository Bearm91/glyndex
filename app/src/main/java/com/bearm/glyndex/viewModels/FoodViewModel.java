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

    public LiveData<List<Food>> getFoodByCategory(Integer categoryId) {
        return foodRepository.getFoodByCategory(categoryId);
    }

    public List<Food> getFoodListByCategory(Integer categoryId) {
        return foodRepository.getFoodListByCategory(categoryId);
    }

    public List<Food> getAllFoodList() {
        return foodRepository.getAllFoodList();
    }

    public List<Food> getFoodByName(String foodName) {
        return foodRepository.getFoodByName(foodName);
    }

    public void insertFood(Food food) {
        foodRepository.insertFood(food);
    }

    public void deleteFood(int foodId){
        foodRepository.deleteFood(foodId);
    }

    public Food getFoodById(int foodId) {
        return foodRepository.getFoodById(foodId);
    }

    public void updateFood(Food food){ foodRepository.updateFood(food);}
}
