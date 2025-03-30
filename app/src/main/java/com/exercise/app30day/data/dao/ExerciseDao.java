package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.items.ExerciseItem;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercise(Exercise exercise);


    @Query("SELECT e.id, e.name, e.description, e.time, e.kcal, e.loopNumber FROM exercise AS e " +
            "JOIN course_day_exercise AS cde " +
            "ON e.id = cde.exerciseId " +
            "WHERE cde.courseId = :courseId AND cde.orderNumber = :orderNumber")
    LiveData<List<ExerciseItem>> getListExerciseItem(int courseId, int orderNumber);

}
