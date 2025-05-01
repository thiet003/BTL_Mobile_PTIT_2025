package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.utils.WeightHistoryItem;

import java.util.List;

@Dao
public interface WeightHistoryDao {

    @Insert
    void insertWeightHistory(WeightHistory weightHistory);

    @Update
    void updateWeightHistory(WeightHistory weightHistory);

    @Query("SELECT wh.id, wh.weight, wh.date FROM weight_history as wh ORDER BY date DESC LIMIT 1")
    LiveData<WeightHistoryItem> getLatestWeightHistoryItem();

    @Query("SELECT wh.id, wh.weight, wh.date FROM weight_history as wh ORDER BY date ASC")
    LiveData<List<WeightHistoryItem>> getAllWeightHistoryItems();
}
