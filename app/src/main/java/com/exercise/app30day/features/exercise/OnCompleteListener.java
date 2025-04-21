package com.exercise.app30day.features.exercise;

import com.exercise.app30day.items.ExerciseItem;

public interface OnCompleteListener {
    void onCompleteExercise(ExerciseItem exerciseItem);
    void onCompleteDay();
}
