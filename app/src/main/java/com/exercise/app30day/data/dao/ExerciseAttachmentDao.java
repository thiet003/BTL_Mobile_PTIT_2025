package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.ExerciseAttachment;

import java.util.List;

@Dao
public interface ExerciseAttachmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExerciseAttachment(ExerciseAttachment exerciseAttachment);

    @Update
    void updateExerciseAttachment(ExerciseAttachment exerciseAttachment);


    @Delete
    void deleteExerciseAttachment(ExerciseAttachment exerciseAttachment);

    @Query("SELECT * FROM exercise_attachment WHERE id = :id")
    LiveData<ExerciseAttachment> getExerciseAttachmentById(int id);

    @Query("SELECT * FROM exercise_attachment order by id asc")
    LiveData<List<ExerciseAttachment>> getAllExerciseAttachments();
}
