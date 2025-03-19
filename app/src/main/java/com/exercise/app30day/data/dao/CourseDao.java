package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.models.CourseItem;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Query("SELECT c.id, c.name, c.difficultLevel, COUNT(cde.id) as numberOfDays FROM course c " +
            "LEFT JOIN course_day_exercise cde ON c.id = cde.courseId " +
            "GROUP BY c.id " +
            "order by c.id asc")
    LiveData<List<CourseItem>> getAllCourseItems();
}
