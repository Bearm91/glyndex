package com.bearm.glyndex.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.bearm.glyndex.models.Category;

import java.util.List;

@Dao
public interface MeasurementDao {

    @Query("SELECT * FROM measurements")
    List<Category> findAll();
}
