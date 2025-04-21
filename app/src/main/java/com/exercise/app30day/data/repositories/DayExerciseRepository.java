package com.exercise.app30day.data.repositories;

public interface DayExerciseRepository {
    void updateDayExercise(int dayId, int exerciseId, boolean completed);
}
