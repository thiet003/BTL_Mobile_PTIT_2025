package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class DayHistoryItem extends BaseItem {

    private int dayId;

    private int exercisePosition;

    private long startTime;

    private long stopTime;

    private long restTime;

    private double kcal;

    private String courseName;

    private int day;

    public DayHistoryItem(int id) {
        super(id);
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public int getExercisePosition() {
        return exercisePosition;
    }

    public void setExercisePosition(int exercisePosition) {
        this.exercisePosition = exercisePosition;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
