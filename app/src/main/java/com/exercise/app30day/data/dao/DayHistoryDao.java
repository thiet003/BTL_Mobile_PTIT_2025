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

    @Query("SELECT DISTINCT dh.id, dh.dayId, dh.exercisePosition, dh.createdAt as startTime, " +
            "dh.stopTime, dh.restTime, dh.kcal, c.name as courseName, d.day as day " +
            "FROM day_history AS dh " +
            "JOIN day AS d ON dh.dayId = d.id " +
            "JOIN course AS c ON d.courseId = c.id " +
            "WHERE dh.dayId = :dayId " +
            "ORDER BY startTime ASC")
    LiveData<List<DayHistoryItem>> getDayHistoryItems(int dayId);

    @Query("DELETE FROM day_history")
    void deleteAll();

    @Query("SELECT DISTINCT dh.id, dh.dayId, dh.exercisePosition, dh.createdAt as startTime, " +
            "dh.stopTime, dh.restTime, dh.kcal, c.name as courseName, d.day as day " +
            "FROM day_history AS dh " +
            "JOIN day AS d ON dh.dayId = d.id " +
            "JOIN course AS c ON d.courseId = c.id " +
            "ORDER BY startTime ASC")
    LiveData<List<DayHistoryItem>> getAllDayHistoryItems();
}
