package com.exercise.app30day.data.models;

import androidx.room.Entity;

@Entity(tableName = "exercise")
public class Exercise extends BaseEntity {
    private String name;
    private String description;

    private long time;

    private double kcal;

    private int loopNumber;

    private String animationFileName;

    public Exercise(String name, String description, long time, double kcal, int loopNumber, String animationFileName) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.kcal = kcal;
        this.loopNumber = loopNumber;
        this.animationFileName = animationFileName;
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

    public String getAnimationFileName() {
        return animationFileName;
    }

    public void setAnimationFileName(String animationFileName) {
        this.animationFileName = animationFileName;
    }
}
