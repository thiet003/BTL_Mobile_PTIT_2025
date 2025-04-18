package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exercise.app30day.data.models.DayTime;
import com.exercise.app30day.items.DayTimeItem;

import java.util.List;

@Dao
public interface DayTimeDao {

    @Insert
    void insertDayTime(DayTime dayTime);

    @Query("SELECT dt.id, dt.dayId, dt.exercisePosition, dt.createdAt as startTime, dt.stopTime, dt.restTime " +
            "FROM day_time AS dt " +
            "WHERE dayId = :dayId " +
            "ORDER BY startTime ASC")
    LiveData<List<DayTimeItem>> getDayTimes(int dayId);
}
