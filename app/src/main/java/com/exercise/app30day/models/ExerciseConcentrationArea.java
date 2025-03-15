package com.exercise.app30day.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "exercise_concentration_area",
        inheritSuperIndices = true,
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ConcentrationArea.class,
                        parentColumns = "id",
                        childColumns = "concentrationAreaId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "exerciseId"),
                @Index(value = "concentrationAreaId")
        }
)
public class ExerciseConcentrationArea extends BaseEntity{
    private int exerciseId;
    private int concentrationAreaId;

    public ExerciseConcentrationArea(int exerciseId, int concentrationAreaId) {
        super();
        this.exerciseId = exerciseId;
        this.concentrationAreaId = concentrationAreaId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getConcentrationAreaId() {
        return concentrationAreaId;
    }

    public void setConcentrationAreaId(int concentrationAreaId) {
        this.concentrationAreaId = concentrationAreaId;
    }
}
