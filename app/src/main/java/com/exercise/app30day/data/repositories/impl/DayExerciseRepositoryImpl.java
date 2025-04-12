package com.exercise.app30day.data.repositories.impl;

import com.exercise.app30day.data.dao.DayExerciseDao;
import com.exercise.app30day.data.repositories.DayExerciseRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DayExerciseRepositoryImpl implements DayExerciseRepository {

    private final DayExerciseDao dayExerciseDao;

    @Inject
    public DayExerciseRepositoryImpl(DayExerciseDao dayExerciseDao) {
        this.dayExerciseDao = dayExerciseDao;
    }

    @Override
    public void updateDayExercise(int dayId, int exerciseId, boolean completed) {
        new Thread(()->{
            dayExerciseDao.updateDayExercise(dayId, exerciseId, completed);
        }).start();
    }
}
