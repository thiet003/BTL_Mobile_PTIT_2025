package com.exercise.app30day.data.models;

import androidx.room.Entity;

@Entity(tableName = "exercise")
public class Exercise extends BaseEntity {
    private String name;
    private String description;

    private long time;

    private double kcal;

    private int loopNumber;

    private String instructionUrl;

    private String animationUrl;


    public Exercise(String name, String description, long time, double kcal, int loopNumber, String animationUrl) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.kcal = kcal;
        this.loopNumber = loopNumber;
        this.animationUrl = animationUrl;
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

    public String getInstructionUrl() {
        return instructionUrl;
    }

    public void setInstructionUrl(String instructionUrl) {
        this.instructionUrl = instructionUrl;
    }

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }
}
