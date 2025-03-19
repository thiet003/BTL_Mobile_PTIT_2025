package com.exercise.app30day.models;

import androidx.room.Ignore;

import com.exercise.app30day.base.adapter.BaseItem;

public class CourseItem extends BaseItem {
    private String name;
    private String difficultLevel;
    private int numberOfDays;
    @Ignore
    private int level;

    public CourseItem(int id, String name, String difficultLevel, int numberOfDays) {
        super(id);
        this.name = name;
        this.difficultLevel = difficultLevel;
        this.numberOfDays = numberOfDays;
        if(difficultLevel.equals("Beginner")){
            level = 1;
        }else if (difficultLevel.equals("Intermediate")){
            level = 2;
        }else{
            level = 3;
        }
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
