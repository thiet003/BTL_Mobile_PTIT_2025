package com.exercise.app30day.features.report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.data.repositories.WeightHistoryRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayHistoryItem;
import com.exercise.app30day.utils.HawkKeys;
import com.exercise.app30day.utils.TimeUtils;
import com.exercise.app30day.utils.WeightHistoryItem;
import com.orhanobut.hawk.Hawk;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReportViewModel extends ViewModel {

    private final DayHistoryRepository dayHistoryRepository;

    private final DayRepository dayRepository;

    private final WeightHistoryRepository weightHistoryRepository;

    private final CourseRepository courseRepository;

    @Inject
    public ReportViewModel(DayHistoryRepository dayHistoryRepository, DayRepository dayRepository, WeightHistoryRepository weightHistoryRepository, CourseRepository courseRepository) {
        this.dayHistoryRepository = dayHistoryRepository;
        this.dayRepository = dayRepository;
        this.weightHistoryRepository = weightHistoryRepository;
        this.courseRepository = courseRepository;
    }

    public LiveData<List<DayHistoryItem>> getAllDayHistoryItems() {
        return dayHistoryRepository.getAllDayHistoryItems();
    }

    public List<DayHistoryItem> getLatestDayHistoryItems(List<DayHistoryItem> dayHistoryItems, int limit) {
        int dataSize = dayHistoryItems.size();
        List<DayHistoryItem> dataItems = dayHistoryItems.subList(Math.max(dataSize - limit, 0), dataSize);
        Collections.reverse(dataItems);
        return dataItems;
    }

    public LiveData<Integer> countCompletedDays() {
        return dayRepository.countCompletedDays();
    }

    public LiveData<WeightHistoryItem> getLatestWeightHistoryItem() {
        return weightHistoryRepository.getLatestWeightHistoryItem();
    }

    public LiveData<List<WeightHistoryItem>> getAllWeightHistoryItems() {
        return weightHistoryRepository.getAllWeightHistoryItems();
    }

    public LiveData<List<CourseItem>> getAllCourseItems() {
        return courseRepository.getAllCourseItems();
    }

    public double calculateTotalMinute(List<DayHistoryItem> dayHistoryItems){
        long totalTime = 0;
        for (DayHistoryItem item : dayHistoryItems){
            totalTime += item.getStopTime() - item.getStartTime();
        }
        return totalTime / 60000.0;
    }

    public double calculateTotalCalories(List<DayHistoryItem> dayHistoryItems){
        double totalCalo = 0;
        for (DayHistoryItem item : dayHistoryItems){
            totalCalo += item.getKcal();
        }
        return totalCalo;
    }

    public void saveWeight(double weight){
        weightHistoryRepository.insertWeightHistory(new WeightHistory(weight, System.currentTimeMillis()));
    }

    public void updateHeight(double height){
        User user = Hawk.get(HawkKeys.INSTANCE_USER_KEY);
        user.setHeight(height);
        Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);
    }
}
