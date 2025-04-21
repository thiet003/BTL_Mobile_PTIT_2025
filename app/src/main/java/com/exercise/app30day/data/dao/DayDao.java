package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.items.DayItem;

import java.util.List;

@Dao
public interface DayDao {
    @Insert
    void insertDays(List<Day> days);

    @Query("SELECT d.id, d.day, " +
            "COUNT(de.id) as numberOfExercises, " +
            "d.completed, " +
            "CASE WHEN d.completed = 1 OR COUNT(CASE WHEN de.completed = 1 THEN 1 ELSE NULL END) = 0 THEN 0 ELSE 1 END AS pending " +
            "FROM day AS d " +
            "LEFT JOIN day_exercise AS de ON d.id = de.dayId " +
            "WHERE d.courseId = :courseId " +
            "GROUP BY d.id " +
            "ORDER BY d.day ASC")
    LiveData<List<DayItem>> getDayItems(int courseId);

    @Query("UPDATE day SET completed = :completed WHERE id = :dayId")
    void updateDay(int dayId, boolean completed);
}
