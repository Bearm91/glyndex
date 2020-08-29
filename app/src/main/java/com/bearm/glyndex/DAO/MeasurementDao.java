package com.bearm.glyndex.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.bearm.glyndex.models.Measurement;

import java.util.List;

@Dao
public interface MeasurementDao {

    @Query("SELECT * FROM measurements")
    List<Measurement> findAll();

    @Query("SELECT * FROM measurements WHERE foodId = :foodId")
    List<Measurement> findByFoodId(Integer foodId);
}
