package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.DayDao;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.items.DayItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DayRepositoryImpl implements DayRepository {


    private final DayDao dayDao;

    @Inject
    public DayRepositoryImpl(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    @Override
    public LiveData<List<DayItem>> getDayItems(int courseId) {
        return dayDao.getDayItems(courseId);
    }

    @Override
    public void updateDay(int dayId, boolean completed) {
        new Thread(() -> dayDao.updateDay(dayId, completed)).start();
    }

    @Override
    public LiveData<Integer> countCompletedDays() {
        return dayDao.countCompletedDays();
    }
}
