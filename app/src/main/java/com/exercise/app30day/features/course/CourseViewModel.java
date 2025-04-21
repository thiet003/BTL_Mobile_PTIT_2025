package com.exercise.app30day.features.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CourseViewModel extends ViewModel {

    private final CourseRepository courseRepository;

    private final DayRepository dayRepository;

    @Inject
    public CourseViewModel(CourseRepository courseRepository, DayRepository dayRepository) {
        this.courseRepository = courseRepository;
        this.dayRepository = dayRepository;
    }

    public LiveData<CourseItem> getCourseItemById(int courseId) {
        return courseRepository.getCourseItemById(courseId);
    }

    public LiveData<List<DayItem>> getDayItems(int courseId) {
        return dayRepository.getDayItems(courseId);
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

    public int findReadyToStartDayPosition(List<DayItem> dayItems){
        for(int position = 0; position < dayItems.size(); position++){
            if (position == 0 && !dayItems.get(position).isCompleted()
                    || (position > 0 && dayItems.get(position - 1).isCompleted() && !dayItems.get(position).isCompleted())){
                return position;
            }
        }
        return 0;
    }
}
