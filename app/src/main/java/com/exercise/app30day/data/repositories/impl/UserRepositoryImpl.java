package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.items.UserItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    @Inject
    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void insertUser(User user) {
        new Thread(()->{
            userDao.insertUser(user);
        }).start();
    }

    @Override
    public LiveData<UserItem> getUserItem() {
        return userDao.getUserItem();
    }

    @Override
    public UserItem getUserItemSync() {
        return userDao.getUserItemSync();
    }

    @Override
    public LiveData<List<UserItem>> getHistoryUserItem() {
        return userDao.getHistoryUserItem();
    }
}
