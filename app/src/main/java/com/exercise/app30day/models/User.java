package com.exercise.app30day.models;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "user", inheritSuperIndices = true)
public class User extends BaseEntity{
    private double height = 0;
    private double weight = 0;

    public User() {
        super();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
