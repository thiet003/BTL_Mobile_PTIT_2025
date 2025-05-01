package com.exercise.app30day.features.report.history;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.items.DayHistoryItem;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.jvm.functions.Function1;

@HiltViewModel
public class WorkoutHistoryViewModel extends ViewModel {

    public final LiveData<List<DayHistoryItem>> onDayHistoryItem;

    @Inject
    public WorkoutHistoryViewModel(DayHistoryRepository dayHistoryRepository) {
        onDayHistoryItem = Transformations.map(dayHistoryRepository.getAllDayHistoryItems(), dayHistoryItems ->{
            Collections.reverse(dayHistoryItems);
            return dayHistoryItems;
        });
    }

    public List<DayHistoryItem> filterHistoryBySameMoth(){
        if(onDayHistoryItem.getValue() != null){
            Calendar currentCalender = Calendar.getInstance();
            int currentMonth = currentCalender.get(Calendar.MONTH);
            int currentYear = currentCalender.get(Calendar.YEAR);
            return onDayHistoryItem.getValue().stream().filter(item -> {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(item.getStopTime());
                return cal.get(Calendar.MONTH) == currentMonth &&
                        cal.get(Calendar.YEAR) == currentYear;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<DayHistoryItem> filterHistoryBySameWeek(){
        if(onDayHistoryItem.getValue() != null){
            Calendar currentCalender = Calendar.getInstance();
            int currentWeek = currentCalender.get(Calendar.WEEK_OF_YEAR);
            int currentYear = currentCalender.get(Calendar.YEAR);
            return onDayHistoryItem.getValue().stream().filter(item -> {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(item.getStopTime());
                return cal.get(Calendar.WEEK_OF_YEAR) == currentWeek &&
                        cal.get(Calendar.YEAR) == currentYear;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<DayHistoryItem> getAllHistory(){
        if(onDayHistoryItem.getValue() != null){
            return onDayHistoryItem.getValue();
        }
        return Collections.emptyList();
    }

    public int countSessionsThisMonth(List<DayHistoryItem> items) {
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH);
        int currentYear = now.get(Calendar.YEAR);

        return (int) items.stream()
                .filter(item -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(item.getStopTime());
                    return cal.get(Calendar.MONTH) == currentMonth &&
                            cal.get(Calendar.YEAR) == currentYear;
                })
                .count();
    }

    public int getLongestWorkoutStreak(List<DayHistoryItem> workouts) {
        if (workouts == null || workouts.isEmpty()) {
            return 0;
        }
        Set<Integer> workoutDays = getIntegers(workouts);

        if (workoutDays.isEmpty()) {
            return 0;
        }

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
