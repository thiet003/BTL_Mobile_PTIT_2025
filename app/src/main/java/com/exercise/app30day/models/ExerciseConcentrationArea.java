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
    private long exerciseId;
    private long concentrationAreaId;

    public ExerciseConcentrationArea(long exerciseId, long concentrationAreaId) {
        super();
        this.exerciseId = exerciseId;
        this.concentrationAreaId = concentrationAreaId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getConcentrationAreaId() {
        return concentrationAreaId;
    }

    public void setConcentrationAreaId(long concentrationAreaId) {
        this.concentrationAreaId = concentrationAreaId;
    }
}
