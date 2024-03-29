package com.bearm.glyndex.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bearm.glyndex.models.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM foods")
    List<Food> findAll();

    @Query("SELECT * FROM foods f WHERE categoryId = :catId ORDER BY f.name ASC")
    LiveData<List<Food>> findFoodByCategory(Integer catId);

    @Query("SELECT * FROM foods f WHERE categoryId = :catId ORDER BY f.name ASC")
    List<Food> findFoodListByCategory(Integer catId);

    @Query("SELECT * FROM foods WHERE id = :foodId")
    Food findById(Integer foodId);

    @Query("SELECT * FROM foods WHERE name like :filter")
    List<Food> findByName(String filter);

    @Insert
    void insert(Food food);

    @Query("DELETE FROM foods WHERE id = :foodId")
    void delete(int foodId);

    @Update
    void update(Food food);
}
