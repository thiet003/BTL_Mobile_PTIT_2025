package com.exercise.app30day.items;

public class WorkoutHistoryItem {
    private String courseName;
    private int dayNumber;
    private String date;
    private long durationMinutes;
    private double calories;
    private long restTimeSeconds;
    private int exercisesCount;

    public WorkoutHistoryItem(String courseName, int dayNumber, String date, long durationMinutes,
                              double calories, long restTimeSeconds, int exercisesCount) {
        this.courseName = courseName;
        this.dayNumber = dayNumber;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.calories = calories;
        this.restTimeSeconds = restTimeSeconds;
        this.exercisesCount = exercisesCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getDate() {
        return date;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public double getCalories() {
        return calories;
    }

    public long getRestTimeSeconds() {
        return restTimeSeconds;
    }

    public int getExercisesCount() {
        return exercisesCount;
    }
}