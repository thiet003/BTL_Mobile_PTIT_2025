package com.exercise.app30day.features.day;

import static com.exercise.app30day.config.AppConfig.LOOP_DURATION_MILLIS;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.DayTimeRepository;
import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.DayTimeItem;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.jvm.functions.Function1;

@HiltViewModel
public class DayViewModel extends ViewModel {

    private final ExerciseRepository exerciseRepository;

    private final DayTimeRepository dayTimeRepository;

    @Inject
    public DayViewModel(ExerciseRepository exerciseRepository, DayTimeRepository dayTimeRepository) {
        this.exerciseRepository = exerciseRepository;
        this.dayTimeRepository = dayTimeRepository;
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
            totalTime += item.getTime() + item.getLoopNumber() * LOOP_DURATION_MILLIS;
        }
        totalTime += (exerciseItems.size() - 1) * 15000L;
        return Math.round(totalTime / 60000.0);
    }

    public LiveData<Integer> getCurrentExercisePosition(int dayId){
        LiveData<List<DayTimeItem>> onDayTimeItem = dayTimeRepository.getDayTimes(dayId);
        return Transformations.map(onDayTimeItem, dayTimeItems -> {
            if (dayTimeItems == null || dayTimeItems.isEmpty()) {
                return 0;
            }
            DayTimeItem lastDayTimeItem = dayTimeItems.get(dayTimeItems.size() - 1);
            return lastDayTimeItem.getExercisePosition();
        });
    }
}
