package com.exercise.app30day.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "history_chats",
    foreignKeys = @ForeignKey(
        entity = Conversation.class,
        parentColumns = "id",
        childColumns = "conversationId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("conversationId")}
)
public class HistoryChat {
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private long conversationId;
    private String role; // "agent" or "user"
    private String message;
    private long createdAt;

    public HistoryChat(long conversationId, String role, String message, long createdAt) {
        this.conversationId = conversationId;
        this.role = role;
        this.message = message;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
