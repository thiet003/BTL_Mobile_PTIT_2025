package com.exercise.app30day.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exercise.app30day.data.models.DayExercise;

import java.util.List;

@Dao
public interface DayExerciseDao {

    @Insert
    void insertDayExercises(List<DayExercise> dayExercises);

    @Query("UPDATE day_exercise SET completed = :completed WHERE dayId = :dayId AND exerciseId = :exerciseId")
    void updateDayExercise(int dayId, int exerciseId, boolean completed);

    @Query("UPDATE day_exercise SET completed = 0")
    void resetAll();
}
