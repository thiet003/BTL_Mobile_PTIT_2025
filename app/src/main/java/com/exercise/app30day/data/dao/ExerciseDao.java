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


    @Query("SELECT e.id, e.name, e.description, e.time, e.kcal, e.loopNumber, ea.fileName as animationFileName, ea.fileType as animationFileType FROM exercise AS e " +
            "JOIN course_day_exercise AS cde ON e.id = cde.exerciseId " +
            "LEFT JOIN exercise_attachment AS ea ON e.id = ea.exerciseId " +
            "WHERE cde.courseId = :courseId " +
            "AND cde.orderNumber = :orderNumber " +
            "AND ea.instructionType = 'animation'" +
            "GROUP BY e.id ")
    LiveData<List<ExerciseItem>> getListExerciseItem(int courseId, int orderNumber);

}
