package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.DayHistoryDao;
import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.items.DayHistoryItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DayHistoryRepositoryImpl implements DayHistoryRepository {


    private final DayHistoryDao dayHistoryDao;

    @Inject
    public DayHistoryRepositoryImpl(DayHistoryDao dayHistoryDao) {
        this.dayHistoryDao = dayHistoryDao;
    }

    @Override
    public void insertDayHistory(DayHistory dayHistory) {
        new Thread(()-> dayHistoryDao.insertDayHistory(dayHistory)).start();
    }

    @Override
    public LiveData<List<DayHistoryItem>> getDayHistoryItems(int dayId) {
        return dayHistoryDao.getDayHistoryItems(dayId);
    }

    @Override
    public LiveData<List<DayHistoryItem>> getAllDayHistoryItems() {
        return dayHistoryDao.getAllDayHistoryItems();
    }
}
