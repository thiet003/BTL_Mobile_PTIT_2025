package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.User;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> getUserById(long userId);

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<User> getUser();
}
