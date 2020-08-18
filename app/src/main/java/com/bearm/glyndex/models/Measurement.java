package com.bearm.glyndex.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName="Measurements", foreignKeys = @ForeignKey(entity = Measurement.class,
        parentColumns = ("id"),
        childColumns = ("foodId"),
        onDelete = ForeignKey.CASCADE))

public class Measurement {
    @PrimaryKey
    private int id;
    private String name;
    private long chrationPerMeasurement;
    private int foodId;



    // Getter Methods

    public long getChrationPerMeasurement() {
        return chrationPerMeasurement;
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

    public void setChrationPerMeasurement(long chrationPerMeasurement) {
        this.chrationPerMeasurement = chrationPerMeasurement;
    }

    public void setFoodId(int food) {
        this.foodId = foodId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measurement() {
    }

    public String toString() {
        return "Measurement{" +
                "chrationPerMeasurement=" + chrationPerMeasurement +
                ", food=" + foodId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
