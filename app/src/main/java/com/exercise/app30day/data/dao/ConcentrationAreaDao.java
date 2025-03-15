package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.ConcentrationArea;

import java.util.List;

@Dao
public interface ConcentrationAreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConcentrationArea(ConcentrationArea concentrationArea);


    @Update
    void updateConcentrationArea(ConcentrationArea concentrationArea);

    @Delete
    void deleteConcentrationArea(ConcentrationArea concentrationArea);

    @Query("SELECT * FROM concentration_area WHERE id = :id")
    LiveData<ConcentrationArea> getConcentrationAreaById(int id);

    @Query("SELECT * FROM concentration_area order by id asc")
    LiveData<List<ConcentrationArea>> getAllConcentrationAreas();


}
