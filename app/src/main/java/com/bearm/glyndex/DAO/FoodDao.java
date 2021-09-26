package com.bearm.glyndex.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.bearm.glyndex.models.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM foods")
    List<Food> findAll();

    @Query("SELECT * FROM foods f WHERE categoryId = :catId ORDER BY f.name ASC")
    List<Food> findFoodByCategory(Integer catId);

    @Query("SELECT * FROM foods WHERE id = :foodId")
    Food findById(Integer foodId);

    @Query("SELECT * FROM foods WHERE name like :filter")
    List<Food> findByName(String filter);
}
