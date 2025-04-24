package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercises(List<Exercise> exercises);

    @Update
    void updateExercises(List<Exercise> exercises);


    @Query("SELECT e.id, e.name, e.description, e.time, e.kcal, e.loopNumber, e.instructionUrl , e.animationUrl FROM exercise AS e " +
            "JOIN day_exercise AS de ON e.id = de.exerciseId " +
            "JOIN day AS d ON de.dayId = d.id " +
            "WHERE d.id = :dayId " +
            "ORDER BY e.id ASC")
    LiveData<List<ExerciseItem>> getExerciseItems(int dayId);

}
