package com.exercise.app30day.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "day_history",
        foreignKeys = @ForeignKey(
                entity = DayExercise.class,
                parentColumns = "id",
                childColumns = "dayId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "dayId")
)
public class DayHistory extends BaseEntity{
    private int dayId;

    private int exercisePosition;

    private long stopTime;

    private long restTime;

    private double kcal;

    public DayHistory(int dayId, int exercisePosition) {
        this.dayId = dayId;
        this.exercisePosition = exercisePosition;
        this.stopTime = 0;
        this.restTime = 0;
        this.kcal = 0;
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
}
