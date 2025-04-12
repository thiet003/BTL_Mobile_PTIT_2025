package com.exercise.app30day.features.day;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DayViewModel extends ViewModel {

    private final ExerciseRepository exerciseRepository;

    @Inject
    public DayViewModel(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public LiveData<List<ExerciseItem>> getExerciseItems(int dayId){
        return exerciseRepository.getExerciseItems(dayId);
    }

    @SuppressLint("DefaultLocale")
    public String calculateAndFormatCalories(List<ExerciseItem> exerciseItems){
        double totalCalo = 0;
        for (ExerciseItem item : exerciseItems){
            totalCalo += item.getKcal();
        }
        return String.format("%.1f", totalCalo).replace(",", ".");
    }

    public long calculateMinutes(List<ExerciseItem> exerciseItems){
        long totalTime = 0;
        for (ExerciseItem item : exerciseItems){
            totalTime += item.getTime() + item.getLoopNumber() * 3000L;
        }
        totalTime += (exerciseItems.size() - 1) * 15000L;
        return Math.round(totalTime / 60000.0);
    }
}
