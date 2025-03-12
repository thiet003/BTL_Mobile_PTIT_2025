package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.models.User;

import java.util.List;

public interface UserRepository {
    void insertUser(User user);

    void updateUser(User user);

    void updateUserById(int id, double height, double weight);

    void deleteUser(User user);

    LiveData<List<User>> getAllUsers();

    LiveData<User> getUserById(int id);
}
