package com.exercise.app30day.data.models;

import androidx.room.Entity;

@Entity(tableName = "weight_history")
public class WeightHistory extends BaseEntity {

    private double weight;

    private long date;

    public WeightHistory(double weight, long date) {
        this.weight = weight;
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
