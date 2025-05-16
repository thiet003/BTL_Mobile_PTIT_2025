package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.models.User;
import com.exercise.app30day.items.UserItem;

import java.util.List;

public interface UserRepository {
    void insertUser(User user);

    LiveData<UserItem> getUserItem();

    UserItem getUserItemSync();

    LiveData<List<UserItem>> getHistoryUserItem();
}
