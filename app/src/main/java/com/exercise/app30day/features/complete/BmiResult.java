package com.exercise.app30day.features.complete;

public class BmiResult {
    private double bmi;
    private String colorHex;
    private String healthStatus;

    public BmiResult(double bmi, String colorHex, String healthStatus) {
        this.bmi = bmi;
        this.colorHex = colorHex;
        this.healthStatus = healthStatus;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
