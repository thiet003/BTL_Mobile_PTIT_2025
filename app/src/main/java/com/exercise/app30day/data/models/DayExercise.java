package com.exercise.app30day.data.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "day_exercise",
        inheritSuperIndices = true,
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId",
                        onDelete = androidx.room.ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Day.class,
                        parentColumns = "id",
                        childColumns = "dayId",
                        onDelete = androidx.room.ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "exerciseId"),
                @Index(value = "dayId")
        }
)
public class DayExercise extends BaseEntity{

    private int exerciseId;

    private int dayId;

    private boolean completed;

    public DayExercise(int exerciseId, int dayId, boolean completed) {
        this.exerciseId = exerciseId;
        this.dayId = dayId;
        this.completed = completed;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
