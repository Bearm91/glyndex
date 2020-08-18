package com.bearm.glyndex;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bearm.glyndex.DAO.CategoryDao;
import com.bearm.glyndex.DAO.FoodDao;
import com.bearm.glyndex.DAO.MeasurementDao;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;

import java.io.File;
import java.io.IOException;

@Database(entities = {Category.class, Food.class, Measurement.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract FoodDao foodDao();

    public abstract MeasurementDao measurementDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        "GI_DATABASE")
                        //.createFromAsset("GI_DATABASE")
                        .fallbackToDestructiveMigration()
                        //.createFromAsset("databases/GI_DATABASE")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                db.execSQL(getCategoryInserts());
                            }

                        })
                        .build();


            }

        }


        return instance;
    }

    private static String getCategoryInserts() {
        String query = "INSERT INTO CATEGORIES VALUES (1,'LÁCTEOS', 'ic_milk'), (2,'OTROS', 'ic_donut'),(3,'LEGUMBRES', 'ic_greenbeans')" +
                ",(4,'TUBÉRCULOS', 'ic_potato')" +
                ",(5,'CEREALES Y HARINAS', 'ic_bread')" +
                ",(6,'FRUTAS', 'ic_watermelon')" +
                ",(7,'BEBIDAS', 'ic_drink')" +
                ", (8,'HORATILZAS', 'ic_carrot')" +
                ", (9,'FRUTA GRASA Y SECA', 'ic_nuts')";
        return query;
    }

}

