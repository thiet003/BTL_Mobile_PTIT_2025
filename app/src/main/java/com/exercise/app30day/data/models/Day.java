package com.exercise.app30day.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "day",
        inheritSuperIndices = true,
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "id",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "courseId")
)
public class Day extends BaseEntity{
    private int courseId;
    private int day;
    private boolean completed;

    public Day(int courseId, int day, boolean completed) {
        this.courseId = courseId;
        this.day = day;
        this.completed = completed;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getOrderNumber() {
        return day;
    }

    public void setOrderNumber(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
