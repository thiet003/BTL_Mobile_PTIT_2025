package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Query("SELECT c.id, c.name, c.difficultLevel, " +
            "COUNT(DISTINCT cde.orderNumber) AS numberOfDays, " +
            "COUNT(DISTINCT cd.orderNumber) AS numberOfCompletedDays " +
            "FROM course c " +
            "LEFT JOIN course_day_exercise cde ON c.id = cde.courseId " +
            "LEFT JOIN complete_day cd ON c.id = cd.courseId " +
            "GROUP BY c.id, c.difficultLevel " +
            "ORDER BY c.id ASC")
    LiveData<List<CourseItem>> getAllCourseItems();

}
