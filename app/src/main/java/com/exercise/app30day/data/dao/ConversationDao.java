package com.exercise.app30day.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exercise.app30day.data.models.Conversation;

import java.util.List;

@Dao
public interface ConversationDao {
    @Insert
    long insert(Conversation conversation);

    @Query("SELECT * FROM conversations WHERE userId = :userId ORDER BY createdAt DESC")
    List<Conversation> getConversationsByUserId(long userId);

    @Query("SELECT * FROM conversations WHERE id = :id")
    Conversation getConversationById(long id);
}
