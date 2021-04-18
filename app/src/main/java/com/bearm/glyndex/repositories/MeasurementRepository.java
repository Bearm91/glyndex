package com.bearm.glyndex.repositories;


import android.app.Application;

import com.bearm.glyndex.AppDatabase;
import com.bearm.glyndex.DAO.MeasurementDao;
import com.bearm.glyndex.models.Measurement;

import java.util.List;

public class MeasurementRepository {

    private MeasurementDao measurementDao;
    private List<Measurement> measurementList;

    public MeasurementRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());

        measurementDao = db.measurementDao();
        measurementList = measurementDao.findAll();

    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public List<Measurement> getMeasurementByFood(Integer foodId) {
        return measurementDao.findByFoodId(foodId);
    }

    public void insertMeasurement(Measurement measurement){
        measurementDao.insert(measurement);
    }

}

