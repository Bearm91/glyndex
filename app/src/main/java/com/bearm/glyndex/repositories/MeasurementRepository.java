package com.bearm.glyndex.repositories;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bearm.glyndex.AppDatabase;
import com.bearm.glyndex.DAO.MeasurementDao;
import com.bearm.glyndex.models.Measurement;

import java.util.List;

public class MeasurementRepository {

    private MeasurementDao measurementDao;

    public MeasurementRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());

        measurementDao = db.measurementDao();

    }

    public LiveData<List<Measurement>> getMeasurementByFood(Integer foodId) {
        return measurementDao.findByFoodId(foodId);
    }

    public Measurement getMeasurementById(Integer measurementId) {
        return measurementDao.findById(measurementId);
    }

    public void insertMeasurement(Measurement measurement){
        measurementDao.insert(measurement);
    }

    public void deleteMeasurement(Measurement measurement){
        measurementDao.delete(measurement);
    }

    public void deleteCustomMeasurements(){
        measurementDao.deleteCustom();
    }
}

