package com.exercise.app30day.features.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.CourseDayExerciseRepository;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CourseViewModel extends ViewModel {

    private final CourseRepository courseRepository;

    private final CourseDayExerciseRepository courseDayExerciseRepository;

    @Inject
    public CourseViewModel(CourseRepository courseRepository, CourseDayExerciseRepository courseDayExerciseRepository) {
        this.courseRepository = courseRepository;
        this.courseDayExerciseRepository = courseDayExerciseRepository;
    }

    public LiveData<CourseItem> getCourseItemById(int courseId) {
        return courseRepository.getCourseItemById(courseId);
    }

    public int calculateDaysRemain(int numberOfCompletedDays, int numberOfDays){
        return Math.max(numberOfDays - numberOfCompletedDays, 0);
    }

    public int getLevel(String difficultLevel) {
        switch (difficultLevel) {
            case "Beginner":
                return 1;
            case "Intermediate":
                return 2;
            default:
                return 3;
        }
    }

    public LiveData<List<DayItem>> getListDay(int courseId) {
        return courseDayExerciseRepository.getListDay(courseId);
    }


    public DayState getExerciseState(DayItem item, DayItem previousItem) {
        if ((previousItem == null && !item.isCompleted()) || (previousItem != null && previousItem.isCompleted() && !item.isCompleted())) {
            return DayState.READY_TO_START;
        } else if (item.isCompleted()) {
            return DayState.COMPLETED;
        } else {
            return DayState.LOCKED;
        }
    }
}
