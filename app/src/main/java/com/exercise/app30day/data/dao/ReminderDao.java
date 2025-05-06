package com.exercise.app30day.data.dao;

import android.graphics.pdf.models.ListItem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.items.ReminderItem;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insertReminder(Reminder reminder);

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);

    @Query("SELECT * FROM reminder AS r ORDER BY r.createdAt ASC")
    LiveData<List<ReminderItem>> getAllReminders();
}
