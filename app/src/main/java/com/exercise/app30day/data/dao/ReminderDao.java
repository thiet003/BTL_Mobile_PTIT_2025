package com.exercise.app30day.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.items.ReminderItem;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertReminder(Reminder reminder);

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);

    @Query("SELECT r.id, r.hour, r.minute, r.isAM, r.daysOfWeek, r.isEnabled FROM reminder AS r ORDER BY r.id DESC")
    LiveData<List<ReminderItem>> getAllReminders();

    @Query("SELECT r.id, r.hour, r.minute, r.isAM, r.daysOfWeek, r.isEnabled FROM reminder AS r ORDER BY r.id DESC")
    List<ReminderItem> getAllRemindersSync();

    @Query("SELECT r.id, r.hour, r.minute, r.isAM, r.daysOfWeek, r.isEnabled FROM reminder AS r WHERE id = :id")
    ReminderItem getReminderByIdSync(int id);
}
