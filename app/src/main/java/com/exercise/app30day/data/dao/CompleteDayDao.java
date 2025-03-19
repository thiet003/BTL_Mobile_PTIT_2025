package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.CompleteDay;

import java.util.List;

@Dao
public interface CompleteDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompleteDay(CompleteDay completeDay);

    @Update
    void updateCompleteDay(CompleteDay completeDay);

    @Delete
    void deleteCompleteDay(CompleteDay completeDay);

    @Query("SELECT * FROM complete_day WHERE id = :id")
    LiveData<CompleteDay> getCompleteDayById(int id);

    @Query("SELECT * FROM complete_day order by id asc")
    LiveData<List<CompleteDay>> getAllCompleteDays();
}
