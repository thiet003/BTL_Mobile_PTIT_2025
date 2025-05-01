package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.utils.WeightHistoryItem;

import java.util.List;

public interface WeightHistoryRepository {
    void insertWeightHistory(WeightHistory weightHistory);
    void updateWeightHistory(WeightHistory weightHistory);
    LiveData<WeightHistoryItem> getLatestWeightHistoryItem();
    LiveData<List<WeightHistoryItem>> getAllWeightHistoryItems();
}
