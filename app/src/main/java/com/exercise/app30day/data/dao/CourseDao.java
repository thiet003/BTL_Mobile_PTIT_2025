package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.items.CourseItem;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourses(List<Course> courses);

    @Update
    void updateCourses(List<Course> courses);

    @Query("SELECT c.id, c.name, c.image, c.type, c.level, " +
            "COUNT(d.id) as numberOfDays, " +
            "COUNT(CASE WHEN d.completed = 1 THEN 1 ELSE NULL END) AS numberOfCompletedDays " +
            "FROM course c " +
            "LEFT JOIN day AS d ON c.id = d.courseId " +
            "GROUP BY c.id " +
            "ORDER BY c.id ASC")
    LiveData<List<CourseItem>> getAllCourseItems();


    @Query("SELECT c.id, c.name, c.image, c.type, c.level, " +
            "COUNT(d.id) as numberOfDays, " +
            "COUNT(CASE WHEN d.completed = 1 THEN 1 ELSE NULL END) AS numberOfCompletedDays " +
            "FROM course c " +
            "LEFT JOIN day AS d ON c.id = d.courseId " +
            "WHERE c.id = :courseId " +
            "GROUP BY c.id " +
            "ORDER BY c.id ASC")
    LiveData<CourseItem> getCourseItemById(int courseId);

}
