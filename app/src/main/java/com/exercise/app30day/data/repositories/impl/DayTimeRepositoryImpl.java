package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.DayTimeDao;
import com.exercise.app30day.data.models.DayTime;
import com.exercise.app30day.data.repositories.DayTimeRepository;
import com.exercise.app30day.items.DayTimeItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DayTimeRepositoryImpl implements DayTimeRepository {


    private final DayTimeDao dayTimeDao;

    @Inject
    public DayTimeRepositoryImpl(DayTimeDao dayTimeDao) {
        this.dayTimeDao = dayTimeDao;
    }

    @Override
    public void insertDayTime(DayTime dayTime) {
        new Thread(()-> dayTimeDao.insertDayTime(dayTime)).start();
    }

    @Override
    public LiveData<List<DayTimeItem>> getDayTimes(int dayId) {
        return dayTimeDao.getDayTimes(dayId);
    }
}
