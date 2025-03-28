package com.exercise.app30day.data.repositories.impl;

import com.exercise.app30day.data.dao.CompleteDayDao;
import com.exercise.app30day.data.models.CompleteDay;
import com.exercise.app30day.data.repositories.CompleteDayRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CompleteDayRepositoryImpl implements CompleteDayRepository {

    private final CompleteDayDao completeDayDao;

    @Inject
    public CompleteDayRepositoryImpl(CompleteDayDao completeDayDao) {
        this.completeDayDao = completeDayDao;
    }

    @Override
    public void insertCompleteDay(CompleteDay completeDay) {
        new Thread(() -> completeDayDao.insertCompleteDay(completeDay)).start();
    }
}
