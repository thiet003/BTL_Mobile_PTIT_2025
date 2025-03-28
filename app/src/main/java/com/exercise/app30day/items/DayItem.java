package com.exercise.app30day.items;

import androidx.annotation.NonNull;

import com.exercise.app30day.base.adapter.BaseItem;

public class DayItem extends BaseItem {

    private int day;

    private int numberOfExercises;

    private boolean isCompleted;
    public DayItem(int id) {
        super(id);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }

    public void setNumberOfExercises(int numberOfExercises) {
        this.numberOfExercises = numberOfExercises;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return "CourseDayExerciseItem{" +
                "day=" + day +
                ", numberOfExercises=" + numberOfExercises +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
