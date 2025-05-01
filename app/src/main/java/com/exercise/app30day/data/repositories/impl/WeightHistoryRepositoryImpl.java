package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.WeightHistoryDao;
import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.data.repositories.WeightHistoryRepository;
import com.exercise.app30day.utils.WeightHistoryItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WeightHistoryRepositoryImpl implements WeightHistoryRepository {

    private final WeightHistoryDao weightHistoryDao;

    @Inject
    public WeightHistoryRepositoryImpl(WeightHistoryDao weightHistoryDao) {
        this.weightHistoryDao = weightHistoryDao;
    }

    @Override
    public void insertWeightHistory(WeightHistory weightHistory) {
        new Thread(()-> weightHistoryDao.insertWeightHistory(weightHistory)).start();
    }

    @Override
    public void updateWeightHistory(WeightHistory weightHistory) {
        new Thread(()-> weightHistoryDao.updateWeightHistory(weightHistory)).start();
    }

    @Override
    public LiveData<WeightHistoryItem> getLatestWeightHistoryItem() {
        return weightHistoryDao.getLatestWeightHistoryItem();
    }

    @Override
    public LiveData<List<WeightHistoryItem>> getAllWeightHistoryItems() {
        return weightHistoryDao.getAllWeightHistoryItems();
    }
}
