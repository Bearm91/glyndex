package com.bearm.glyndex.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bearm.glyndex.models.Measurement;
import com.bearm.glyndex.repositories.MeasurementRepository;

import java.util.List;

public class MeasurementViewModel extends AndroidViewModel {

    MeasurementRepository measurementRepository;

    public MeasurementViewModel(@NonNull Application application) {
        super(application);

        measurementRepository = new MeasurementRepository(application);
    }


    public LiveData<List<Measurement>> getMeasurementByFood(Integer foodId) {
        return measurementRepository.getMeasurementByFood(foodId);
    }

    public void deleteMeasurement(Measurement measurement) {
        measurementRepository.deleteMeasurement(measurement);
    }

    public void insertMeasurement(Measurement measurement) {
        measurementRepository.insertMeasurement(measurement);
    }
}
