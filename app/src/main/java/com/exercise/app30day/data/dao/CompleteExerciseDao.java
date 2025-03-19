package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.CompleteExercise;

import java.util.List;

@Dao
public interface CompleteExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompleteExercise(CompleteExercise completeExercise);

    @Update
    void updateCompleteExercise(CompleteExercise completeExercise);

    @Delete
    void deleteCompleteExercise(CompleteExercise completeExercise);

    @Query("SELECT * FROM complete_exercise WHERE id = :id")
    LiveData<CompleteExercise> getCompleteExerciseById(int id);

    @Query("SELECT * FROM complete_exercise order by id asc")
    LiveData<List<CompleteExercise>> getAllCompleteExercises();
}
