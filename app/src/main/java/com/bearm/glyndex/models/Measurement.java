package com.bearm.glyndex.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Measurements", foreignKeys = @ForeignKey(entity = Food.class,
        parentColumns = ("id"),
        childColumns = ("foodId"),
        onDelete = ForeignKey.CASCADE),
        indices = {
                @Index(value = "foodId"),
                @Index(value = "id", unique = true)
        })

public class Measurement {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "chRationPerMeasurement")
    private float chRationPerMeasurement;
    @ColumnInfo(name = "foodId")
    private int foodId;

    @ColumnInfo(name = "custom")
    private boolean isCustom;

    // Getter Methods

    public float getChRationPerMeasurement() {
        return chRationPerMeasurement;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setChRationPerMeasurement(float chRationPerMeasurement) {
        this.chRationPerMeasurement = chRationPerMeasurement;
    }

    public void setFoodId(int food) {
        this.foodId = food;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measurement() {
    }

    public Measurement(String name, float chQuantity, int foodId) {
        this.name = name;
        this.chRationPerMeasurement = chQuantity;
        this.foodId = foodId;
        this.isCustom = true;
    }

    public String toString() {
        return "Measurement{" +
                "chrationPerMeasurement=" + chRationPerMeasurement +
                ", food=" + foodId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", isCustom='" + isCustom + '\'' +
                '}';
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
