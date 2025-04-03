package com.exercise.app30day.data.repositories.impl;

import com.exercise.app30day.data.dao.CompleteExerciseDao;
import com.exercise.app30day.data.models.CompleteExercise;
import com.exercise.app30day.data.repositories.CompleteExerciseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CompleteExerciseRepositoryImpl implements CompleteExerciseRepository {

    private final CompleteExerciseDao completeExerciseDao;

    @Inject
    public CompleteExerciseRepositoryImpl(CompleteExerciseDao completeExerciseDao) {
        this.completeExerciseDao = completeExerciseDao;
    }

    @Override
    public void insertCompleteExercise(CompleteExercise completeExercise) {
        new Thread(() -> completeExerciseDao.insertCompleteExercise(completeExercise)).start();
    }
}
