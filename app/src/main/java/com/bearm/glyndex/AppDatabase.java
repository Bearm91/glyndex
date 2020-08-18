package com.bearm.glyndex;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bearm.glyndex.DAO.CategoryDao;
import com.bearm.glyndex.DAO.FoodDao;
import com.bearm.glyndex.DAO.MeasurementDao;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;

@Database(entities = {Category.class, Food.class, Measurement.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract FoodDao foodDao();

    public abstract MeasurementDao measurementDao();

    private static AppDatabase instance;

    private static String ASSET_DIR;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                ASSET_DIR = "databases/GI_DATABASE.db";
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        "GI_DATABASE.db")
                        .fallbackToDestructiveMigration()
                        .createFromAsset(ASSET_DIR)
                        .allowMainThreadQueries()
                        .build();
            }

        }

        return instance;
    }

}

