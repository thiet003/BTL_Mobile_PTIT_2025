package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.ExerciseConcentrationArea;

import java.util.List;

@Dao
public interface ExerciseConcentrationAreaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseConcentrationAreas(List<ExerciseConcentrationArea> exerciseConcentrationAreas);
}
