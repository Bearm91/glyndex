package com.bearm.glyndex;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bearm.glyndex.DAO.CategoryDao;
import com.bearm.glyndex.DAO.FoodDao;
import com.bearm.glyndex.DAO.MeasurementDao;
import com.bearm.glyndex.models.Category;
import com.bearm.glyndex.models.Food;
import com.bearm.glyndex.models.Measurement;

@Database(entities = {Category.class, Food.class, Measurement.class}, version = 2)
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
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }

        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Measurements ADD COLUMN custom INTEGER NOT NULL DEFAULT 0");
            database.execSQL("CREATE UNIQUE INDEX index_Categories_id ON Categories(id)");
            database.execSQL("CREATE INDEX index_Foods_categoryId ON Foods(categoryId)");
            database.execSQL("CREATE UNIQUE INDEX index_Foods_id ON Foods(id)");
            database.execSQL("CREATE UNIQUE INDEX index_Foods_name ON Foods(name)");
            database.execSQL("CREATE INDEX index_Measurements_foodId ON Measurements(foodId)");
            database.execSQL("CREATE UNIQUE INDEX index_Measurements_id ON Measurements(id)");

        }
    };
}

