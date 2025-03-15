package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.ExerciseConcentrationArea;

import java.util.List;

@Dao
public interface ExerciseConcentrationAreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseConcentrationArea(ExerciseConcentrationArea exerciseConcentrationArea);

    @Update
    void updateExerciseConcentrationArea(ExerciseConcentrationArea exerciseConcentrationArea);

    @Delete
    void deleteExerciseConcentrationArea(ExerciseConcentrationArea exerciseConcentrationArea);

    @Query("SELECT * FROM exercise_concentration_area WHERE id = :id")
    LiveData<ExerciseConcentrationArea> getExerciseConcentrationAreaById(int id);

    @Query("SELECT * FROM exercise_concentration_area order by id asc")
    LiveData<List<ExerciseConcentrationArea>> getAllExerciseConcentrationAreas();
}
