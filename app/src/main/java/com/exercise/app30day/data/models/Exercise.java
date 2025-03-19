package com.exercise.app30day.data.models;

import androidx.room.Entity;

@Entity(tableName = "exercise", inheritSuperIndices = true)
public class Exercise extends BaseEntity {
    private String name;
    private String description;

    private long time;

    private double kcal;

    private int loopNumber;

    public Exercise(String name, String description, long time, double kcal, int loopNumber) {
        super();
        this.name = name;
        this.description = description;
        this.time = time;
        this.kcal = kcal;
        this.loopNumber = loopNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getLoopNumber() {
        return loopNumber;
    }

    public void setLoopNumber(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
