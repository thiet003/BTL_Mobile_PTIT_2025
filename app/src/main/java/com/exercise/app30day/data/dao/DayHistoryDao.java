package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.items.DayHistoryItem;

import java.util.List;

@Dao
public interface DayHistoryDao {

    @Insert
    void insertDayHistory(DayHistory dayHistory);

    @Query("SELECT dh.id, dh.dayId, dh.exercisePosition, dh.createdAt as startTime, dh.stopTime, dh.restTime " +
            "FROM day_history AS dh " +
            "WHERE dh.dayId = :dayId " +
            "ORDER BY startTime ASC")
    LiveData<List<DayHistoryItem>> getDayHistoryItems(int dayId);
}
