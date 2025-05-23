package com.exercise.app30day.data.repositories.impl;

import androidx.lifecycle.LiveData;

import com.exercise.app30day.data.dao.ReminderDao;
import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReminderRepositoryImpl implements ReminderRepository {

    private final ReminderDao reminderDao;

    @Inject
    public ReminderRepositoryImpl(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
    }

    @Override
    public long insertReminderSync(Reminder reminder) {
        return reminderDao.insertReminder(reminder);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        new Thread(()-> reminderDao.updateReminder(reminder)).start();
    }

    @Override
    public void deleteReminder(Reminder reminder) {
        new Thread(()-> reminderDao.deleteReminder(reminder)).start();
    }

    @Override
    public LiveData<List<ReminderItem>> getAllReminders() {
        return reminderDao.getAllReminders();
    }

    @Override
    public List<ReminderItem> getAllRemindersSync() {
        return reminderDao.getAllRemindersSync();
    }

    @Override
    public ReminderItem getReminderByIdSync(int id) {
        return reminderDao.getReminderByIdSync(id);
    }
}
