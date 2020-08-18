package com.bearm.glyndex.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="Categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="iconName")
    private String iconName;

    public Category() {
    }
    // Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
