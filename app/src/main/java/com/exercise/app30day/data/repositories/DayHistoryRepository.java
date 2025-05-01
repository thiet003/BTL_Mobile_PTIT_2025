package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.items.DayHistoryItem;

import java.util.List;

public interface DayHistoryRepository {

    void insertDayHistory(DayHistory dayHistory);

    LiveData<List<DayHistoryItem>> getDayHistoryItems(int dayId);

    LiveData<List<DayHistoryItem>> getAllDayHistoryItems();
}
