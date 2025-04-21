package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

public interface ExerciseRepository {
    LiveData<List<ExerciseItem>> getExerciseItems(int dayId);
}
