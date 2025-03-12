package com.exercise.app30day.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "course_day_exercise",
        inheritSuperIndices = true,
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = "id",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "exerciseId"),
                @Index(value = "courseId")
        }
)
public class CourseDayExercise {
    private int exerciseId;
    private int courseId;

    private int orderNumber;

    public CourseDayExercise(int exerciseId, int courseId, int orderNumber) {
        super();
        this.exerciseId = exerciseId;
        this.courseId = courseId;
        this.orderNumber = orderNumber;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
