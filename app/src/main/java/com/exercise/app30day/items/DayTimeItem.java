package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class DayTimeItem extends BaseItem {

    private int dayId;

    private int exercisePosition;

    private long startTime;

    private long stopTime;

    public DayTimeItem(int id) {
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
}
