package com.exercise.app30day.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exercise.app30day.data.models.HistoryChat;

import java.util.List;

@Dao
public interface HistoryChatDao {
    @Insert
    long insert(HistoryChat historyChat);

    @Query("SELECT * FROM history_chats WHERE conversationId = :conversationId ORDER BY createdAt ASC")
    List<HistoryChat> getHistoryChatsByConversationId(long conversationId);
}
