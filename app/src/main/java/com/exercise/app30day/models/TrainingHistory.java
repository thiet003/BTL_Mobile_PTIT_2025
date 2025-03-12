package com.exercise.app30day.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "training_history",
        inheritSuperIndices = true,
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
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
                @Index(value = "userId"),
                @Index(value = "courseId")
        }
)
public class TrainingHistory extends BaseEntity{
    private int userId;
    private int courseId;

    private int orderNumber;

    private long time;

    private double kcal;

    public TrainingHistory(int userId, int courseId, int orderNumber, long time, double kcal) {
        super();
        this.userId = userId;
        this.courseId = courseId;
        this.orderNumber = orderNumber;
        this.time = time;
        this.kcal = kcal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
}
