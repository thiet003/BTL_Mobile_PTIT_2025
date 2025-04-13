package com.exercise.app30day.features.complete;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

public class BmiResult {
    private double bmi;
    @ColorRes
    private int color;

    @StringRes
    private int healthStatus;

    public BmiResult(double bmi, int color, int healthStatus) {
        this.bmi = bmi;
        this.color = color;
        this.healthStatus = healthStatus;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(int healthStatus) {
        this.healthStatus = healthStatus;
    }


}
