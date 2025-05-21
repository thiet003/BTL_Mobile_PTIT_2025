package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.items.ReminderItem;

import java.util.List;

public interface ReminderRepository {
    long insertReminderSync(Reminder reminder);

    void updateReminder(Reminder reminder);

    void deleteReminder(Reminder reminder);

    LiveData<List<ReminderItem>> getAllReminders();

    List<ReminderItem> getAllRemindersSync();

    ReminderItem getReminderByIdSync(int id);
}
