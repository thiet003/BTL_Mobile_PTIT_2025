package com.exercise.app30day.data.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "complete_exercise",
        inheritSuperIndices = true,
        foreignKeys = {
                @ForeignKey(
                        entity = CourseDayExercise.class,
                        parentColumns = "id",
                        childColumns = "courseDayExerciseId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "courseDayExerciseId"),
                @Index(value = "userId")
        }
)
public class CompleteExercise extends BaseEntity {

    private int userId;

    private int courseDayExerciseId;

    private String status;

    public CompleteExercise(int userId, int courseDayExerciseId, String status) {
        super();
        this.userId = userId;
        this.courseDayExerciseId = courseDayExerciseId;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseDayExerciseId() {
        return courseDayExerciseId;
    }

    public void setCourseDayExerciseId(int courseDayExerciseId) {
        this.courseDayExerciseId = courseDayExerciseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
