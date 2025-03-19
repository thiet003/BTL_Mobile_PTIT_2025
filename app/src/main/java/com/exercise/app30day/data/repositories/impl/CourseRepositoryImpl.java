package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.models.CourseItem;

import java.util.List;

import javax.inject.Inject;

public class CourseRepositoryImpl implements CourseRepository {

    private final CourseDao courseDao;

    @Inject
    public CourseRepositoryImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    @Override
    public LiveData<List<CourseItem>> getAllCourseItems() {
        return courseDao.getAllCourseItems();
    }
}
