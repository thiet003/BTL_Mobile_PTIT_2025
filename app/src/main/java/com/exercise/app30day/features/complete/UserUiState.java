package com.exercise.app30day.features.complete;

import java.util.Calendar;

public class UserUiState {
    private int userGenderIndex = 2;

    private double weight = 70;

    private double height = 170;

    private Calendar calendar = Calendar.getInstance();

    public int getUserGenderIndex() {
        return userGenderIndex;
    }

    public void setUserGenderIndex(int userGenderIndex) {
        this.userGenderIndex = userGenderIndex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
