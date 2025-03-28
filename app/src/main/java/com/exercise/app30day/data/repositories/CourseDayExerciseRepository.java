package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.items.DayItem;

import java.util.List;

public interface CourseDayExerciseRepository {
    LiveData<List<DayItem>> getListDay(int courseId);
}
