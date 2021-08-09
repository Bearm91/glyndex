package com.bearm.glyndex.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bearm.glyndex.models.Measurement;

import java.util.List;

@Dao
public interface MeasurementDao {

    @Query("SELECT * FROM measurements")
    LiveData<List<Measurement>> findAll();

    @Query("SELECT * FROM measurements WHERE foodId = :foodId ORDER BY chRationPerMeasurement")
    LiveData<List<Measurement>>  findByFoodId(Integer foodId);

    @Insert
    void insert(Measurement measurement);

    @Delete
    void delete(Measurement measurement);

    @Query("SELECT * FROM measurements where id = :measurementId")
    Measurement findById(Integer measurementId);

    @Query("DELETE FROM measurements WHERE custom = 1")
    int deleteCustom();
}
