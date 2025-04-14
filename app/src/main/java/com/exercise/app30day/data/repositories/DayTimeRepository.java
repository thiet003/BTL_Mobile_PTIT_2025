package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.models.DayTime;
import com.exercise.app30day.items.DayTimeItem;

import java.util.List;

public interface DayTimeRepository {

    void insertDayTime(DayTime dayTime);

    LiveData<List<DayTimeItem>> getDayTimes(int dayId);
}
