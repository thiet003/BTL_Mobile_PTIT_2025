package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
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

    @Override
    public LiveData<CourseItem> getCourseItemById(int courseId) {
        return courseDao.getCourseItemById(courseId);
    }

    @Override
    public CourseItem getCurrentCourseItemSync() {
        return courseDao.getCurrentCourseItemSync();
    }
}
