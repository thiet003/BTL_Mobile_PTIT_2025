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
        userDao.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void updateUserById(int id, double height, double weight) {
        userDao.updateUserById(id, height, weight);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
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
