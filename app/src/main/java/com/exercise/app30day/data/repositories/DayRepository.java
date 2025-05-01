package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.exercise.app30day.items.DayItem;

import java.util.List;

public interface DayRepository {
    LiveData<List<DayItem>> getDayItems(int courseId);

    void updateDay(int dayId, boolean completed);

    LiveData<Integer> countCompletedDays();
}
