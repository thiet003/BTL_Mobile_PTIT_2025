package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.ConcentrationArea;

import java.util.List;

@Dao
public interface ConcentrationAreaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConcentrationAreas(List<ConcentrationArea> concentrationAreas);
}
