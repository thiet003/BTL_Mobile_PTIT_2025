package com.exercise.app30day.features.complete;

import java.util.Calendar;

public class UserUiState {
    private int userGenderIndex = 2;

    private int weight = 70;

    private int height = 170;

    private Calendar calendar = Calendar.getInstance();

    public int getUserGenderIndex() {
        return userGenderIndex;
    }

    public void setUserGenderIndex(int userGenderIndex) {
        this.userGenderIndex = userGenderIndex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
