package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.models.CourseItem;

import java.util.List;

public interface CourseRepository {
    LiveData<List<CourseItem>> getAllCourseItems();
}
