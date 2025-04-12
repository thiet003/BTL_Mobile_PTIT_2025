package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final ExerciseDao exerciseDao;

    @Inject
    public ExerciseRepositoryImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public LiveData<List<ExerciseItem>> getExerciseItems(int dayId) {
        return exerciseDao.getExerciseItems(dayId);
    }
}
