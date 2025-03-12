package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.models.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public void insertUser(User user) {
        new Thread(() -> userDao.insertUser(user)).start();
    }

    @Override
    public void updateUser(User user) {
        new Thread(() -> userDao.updateUser(user)).start();
    }

    @Override
    public void deleteUser(User user) {
        new Thread(() -> userDao.deleteUser(user)).start();
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public LiveData<User> getUserById(int id) {
        return userDao.getUserById(id);
    }
}
