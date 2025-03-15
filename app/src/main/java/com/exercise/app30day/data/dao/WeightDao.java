package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.Weight;

import java.util.List;

@Dao
public interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);

    @Query("SELECT * FROM weight WHERE id = :id")
    LiveData<Weight> getWeightById(int id);

    @Query("SELECT * FROM weight order by id asc")
    LiveData<List<Weight>> getAllWeights();
}
