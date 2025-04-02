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

    private String fileType;

    private String instructionType;

    public ExerciseAttachment(int exerciseId, String fileName, String fileType, String instructionType) {
        super();
        this.exerciseId = exerciseId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.instructionType = instructionType;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }
}
