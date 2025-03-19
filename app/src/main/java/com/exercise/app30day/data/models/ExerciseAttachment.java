package com.exercise.app30day.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "exercise_attachment",
        inheritSuperIndices = true,
        foreignKeys = @ForeignKey(
                entity = Exercise.class,
                parentColumns = "id",
                childColumns = "exerciseId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "exerciseId")
)
public class ExerciseAttachment extends BaseEntity {
    private int exerciseId;

    private String fileName;

    private String type;

    public ExerciseAttachment(int exerciseId, String fileName, String type) {
        super();
        this.exerciseId = exerciseId;
        this.fileName = fileName;
        this.type = type;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
