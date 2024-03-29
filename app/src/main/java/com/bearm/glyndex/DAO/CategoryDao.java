package com.bearm.glyndex.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.bearm.glyndex.models.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    List<Category> findAll();

    @Query("SELECT iconName FROM categories WHERE name = :categoryName")
    String findIconNameByCatName(String categoryName);

    @Query("SELECT * FROM categories WHERE id in (SELECT categoryId FROM foods where id = :foodId)")
    Category findByFoodId(int foodId);

    @Query("SELECT * FROM categories WHERE id = :id")
    Category findById(int id);
}
