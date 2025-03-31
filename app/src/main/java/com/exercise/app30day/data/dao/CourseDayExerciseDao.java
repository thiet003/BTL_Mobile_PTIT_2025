package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exercise.app30day.data.models.CourseDayExercise;
import com.exercise.app30day.items.DayItem;

import java.util.List;

@Dao
public interface CourseDayExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseDayExercises(List<CourseDayExercise> courseDayExercises);
    @Query("SELECT cde.id, " +
            "cde.orderNumber AS day, " +
            "COUNT(cde.exerciseId) AS numberOfExercises, " +
            "(COUNT(DISTINCT cd.id) > 0) AS isCompleted " +
            "FROM course_day_exercise AS cde " +
            "LEFT JOIN complete_day AS cd ON cde.orderNumber = cd.orderNumber AND cde.courseId = cd.courseId " +
            "WHERE cde.courseId = :courseId " +
            "GROUP BY cde.orderNumber " +
            "ORDER BY cde.orderNumber ASC")
    LiveData<List<DayItem>> getListDay(int courseId);

}
