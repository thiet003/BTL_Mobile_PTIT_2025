package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.CourseDayExerciseDao;
import com.exercise.app30day.data.repositories.CourseDayExerciseRepository;
import com.exercise.app30day.items.DayItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CourseDayExerciseRepositoryImpl implements CourseDayExerciseRepository {

    private final CourseDayExerciseDao courseDayExerciseDao;

    @Inject
    public CourseDayExerciseRepositoryImpl(CourseDayExerciseDao courseDayExerciseDao) {
        this.courseDayExerciseDao = courseDayExerciseDao;
    }

    @Override
    public LiveData<List<DayItem>> getListDay(int courseId) {
        return courseDayExerciseDao.getListDay(courseId);
    }
}
