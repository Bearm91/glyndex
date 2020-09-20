package com.bearm.glyndex.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Foods", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = ("id"),
        childColumns = ("categoryId"),
        onDelete = ForeignKey.CASCADE),
        indices = {
                @Index(value = "id", unique = true),
                @Index(value = "categoryId"),
                @Index(value = "name", unique = true)
        })

public class Food {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "categoryId")
    private int category;
    @ColumnInfo(name = "gI")
    private Integer gI;
    @ColumnInfo(name = "gramsPerChRation")
    private long gramsPerChRation;


    public Food() {
    }

    // Getter Methods

    public int getCategory() {
        return category;
    }

    public Integer getGI() {
        return gI;
    }

    public long getGramsPerChRation() {
        return gramsPerChRation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setCategory(int category_id) {
        this.category = category_id;
    }

    public void setGI(Integer gI) {
        this.gI = gI;
    }

    public void setGramsPerChRation(long gramsPerChRation) {
        this.gramsPerChRation = gramsPerChRation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Food{" +
                "categoryId='" + category + '\'' +
                ", GI='" + gI + '\'' +
                ", gramsPerChRation='" + gramsPerChRation + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}