package com.bearm.glyndex.repositories;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bearm.glyndex.AppDatabase;
import com.bearm.glyndex.DAO.FoodDao;
import com.bearm.glyndex.models.Food;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private List<Food> foodList;
    private int categoryId;

    public FoodRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());

        foodDao = db.foodDao();
        foodList = foodDao.findAll();

    }

    public List<Food> getAllFoodList() {
        return foodList;
    }

    public LiveData<List<Food>> getFoodByCategory(Integer categoryId) {
        return foodDao.findFoodByCategory(categoryId);
    }

    public List<Food> getFoodListByCategory(Integer categoryId) {
        return foodDao.findFoodListByCategory(categoryId);
    }

    public Food getFoodById(Integer foodId) {
        return foodDao.findById(foodId);
    }

    public List<Food> getFoodByName(String filter) {
        return foodDao.findByName(filter);
    }

    public void insertFood(Food food){
        foodDao.insert(food);
    }

    public void deleteFood(int foodId) {
        foodDao.delete(foodId);
    }

    public void updateFood(Food food) {
        foodDao.update(food);
    }
}

