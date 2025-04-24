package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class CourseItem extends BaseItem {
    private String name;
    private String image;
    private int numberOfDays;
    private int numberOfCompletedDays;

    private String type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDayProgress() {
        return (int) (numberOfCompletedDays * 100.0 / numberOfDays);
    }

    public int getRemainDays(){
        return Math.max(numberOfDays - numberOfCompletedDays, 0);
    }
}
