package com.exercise.app30day.features.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayHistoryItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.jvm.functions.Function1;


@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final CourseRepository courseRepository;

    private final DayHistoryRepository dayHistoryRepository;


    @Inject
    public HomeViewModel(CourseRepository courseRepository, DayHistoryRepository dayHistoryRepository) {
        this.courseRepository = courseRepository;
        this.dayHistoryRepository = dayHistoryRepository;
    }

    public LiveData<List<CourseItem>> getAllCourseItems() {
        return courseRepository.getAllCourseItems();
    }

    public LiveData<Integer> getLongestWorkoutStreak(){
        return Transformations.map(dayHistoryRepository.getAllDayHistoryItems(), this::getLongestWorkoutStreak);
    }

    private int getLongestWorkoutStreak(List<DayHistoryItem> workouts) {
        if (workouts == null || workouts.isEmpty()) {
            return 0;
        }
        Set<Integer> workoutDays = getIntegers(workouts);

        List<Integer> sortedDays = new ArrayList<>(workoutDays);
        Collections.sort(sortedDays);

        int maxStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < sortedDays.size(); i++) {
            if (sortedDays.get(i) == sortedDays.get(i-1) + 1) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
        }
        maxStreak = Math.max(maxStreak, currentStreak);

        return maxStreak;
    }

    private Set<Integer> getIntegers(List<DayHistoryItem> workouts) {
        Set<Integer> workoutDays = new HashSet<>();
        Calendar calendar = Calendar.getInstance();

        for (DayHistoryItem workout : workouts) {
            long endTimeMillis = workout.getStopTime();
            if (endTimeMillis > 0) {
                calendar.setTimeInMillis(endTimeMillis);
                int daysSinceEpoch = (int) (calendar.getTimeInMillis() / (24 * 60 * 60 * 1000));
                workoutDays.add(daysSinceEpoch);
            }
        }
        return workoutDays;
    }
}
