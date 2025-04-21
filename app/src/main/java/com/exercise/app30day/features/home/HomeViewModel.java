package com.exercise.app30day.features.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final CourseRepository courseRepository;


    @Inject
    public HomeViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public LiveData<List<CourseItem>> getAllCourseItems() {
        return courseRepository.getAllCourseItems();
    }
}
