package com.exercise.app30day.items;

import androidx.room.Ignore;

import com.exercise.app30day.base.adapter.BaseItem;

public class CourseItem extends BaseItem {
    private String name;
    private String difficultLevel;
    private int numberOfDays;

    private int numberOfCompletedDays;

    @Ignore
    private double dayProgress;

    @Ignore
    private int level;

    public CourseItem(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfCompletedDays() {
        return numberOfCompletedDays;
    }

    public void setNumberOfCompletedDays(int numberOfCompletedDays) {
        this.numberOfCompletedDays = numberOfCompletedDays;
    }

    public double getDayProgress() {
        return dayProgress;
    }

    public void setDayProgress(double dayProgress) {
        this.dayProgress = dayProgress;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
