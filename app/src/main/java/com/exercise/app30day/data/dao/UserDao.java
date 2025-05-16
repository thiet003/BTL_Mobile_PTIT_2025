package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.User;
import com.exercise.app30day.items.UserItem;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT id, name, weight, height, createdAt AS date FROM user order by createdAt DESC LIMIT 1")
    LiveData<UserItem> getUserItem();

    @Query("SELECT id, name, weight, height, createdAt AS date FROM user order by createdAt DESC LIMIT 1")
    UserItem getUserItemSync();

    @Query("SELECT id, name, weight, height, createdAt AS date FROM user order by createdAt ASC")
    LiveData<List<UserItem>> getHistoryUserItem();
}
