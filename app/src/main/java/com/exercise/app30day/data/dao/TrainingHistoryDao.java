package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.TrainingHistory;

import java.util.List;

@Dao
public interface TrainingHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrainingHistory(TrainingHistory trainingHistory);

    @Update
    void updateTrainingHistory(TrainingHistory trainingHistory);

    @Delete
    void deleteTrainingHistory(TrainingHistory trainingHistory);

    @Query("SELECT * FROM training_history order by id asc")
    LiveData<List<TrainingHistory>> getAllTrainingHistories();

    @Query("SELECT * FROM training_history WHERE id = :id")
    LiveData<TrainingHistory> getTrainingHistoryById(int id);
}
