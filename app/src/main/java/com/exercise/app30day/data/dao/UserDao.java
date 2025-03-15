package com.exercise.app30day.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("UPDATE user SET height = :height, weight = :weight WHERE id = :id")
    void updateUserById(int id, double height, double weight);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM user order by id asc")
    LiveData<List<User>> getAllUsers();
}
