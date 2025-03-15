package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.CourseDayExercise;

import java.util.List;

@Dao
public interface CourseDayExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseDayExercise(CourseDayExercise courseDayExercise);

    @Update
    void updateCourseDayExercise(CourseDayExercise courseDayExercise);

    @Delete
    void deleteCourseDayExercise(CourseDayExercise courseDayExercise);

    @Query("SELECT * FROM course_day_exercise WHERE id = :id")
    LiveData<CourseDayExercise> getCourseDayExerciseById(int id);

    @Query("SELECT * FROM course_day_exercise order by id asc")
    LiveData<List<CourseDayExercise>> getAllCourseDayExercises();

}
