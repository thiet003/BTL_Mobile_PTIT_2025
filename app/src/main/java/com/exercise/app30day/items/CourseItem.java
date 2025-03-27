package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class CourseItem extends BaseItem {
    private String name;
    private String difficultLevel;
    private int numberOfDays;

    private int numberOfCompletedDays;

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
}
