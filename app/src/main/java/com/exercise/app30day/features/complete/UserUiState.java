package com.exercise.app30day.features.complete;

public class UserUiState {
    private int genderFocusedIndex = 2;

    private int weightPickerIndex = 40;

    private int heightPickerIndex = 40;

    public int getGenderFocusedIndex() {
        return genderFocusedIndex;
    }

    public void setGenderFocusedIndex(int genderFocusedIndex) {
        this.genderFocusedIndex = genderFocusedIndex;
    }

    public int getWeightPickerIndex() {
        return weightPickerIndex;
    }

    public void setWeightPickerIndex(int weightPickerIndex) {
        this.weightPickerIndex = weightPickerIndex;
    }

    public int getHeightPickerIndex() {
        return heightPickerIndex;
    }

    public void setHeightPickerIndex(int heightPickerIndex) {
        this.heightPickerIndex = heightPickerIndex;
    }
}
