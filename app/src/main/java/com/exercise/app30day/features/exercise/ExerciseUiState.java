package com.exercise.app30day.features.exercise;

import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

public class ExerciseUiState {

    private int exercisePosition = 0;

    private ExerciseState exerciseState = ExerciseState.PREPARE;

    public int getExercisePosition() {
        return exercisePosition;
    }

    public void setExercisePosition(int exercisePosition) {
        this.exercisePosition = exercisePosition;
    }

    public ExerciseState getExerciseState() {
        return exerciseState;
    }

    public void setExerciseState(ExerciseState exerciseState) {
        this.exerciseState = exerciseState;
    }
}
