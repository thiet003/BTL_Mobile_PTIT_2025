package com.exercise.app30day.features.setting.reminder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReminderViewModel extends ViewModel {
    private final ReminderRepository reminderRepository;

    @Inject
    public ReminderViewModel(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void insertReminder(ReminderItem reminderItem) {
        reminderRepository.insertReminder(convertToReminder(reminderItem));
    }

    public void updateReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.updateReminder(reminder);
    }

    public void deleteReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.deleteReminder(reminder);
    }

    public LiveData<List<ReminderItem>> getAllReminders() {
        return reminderRepository.getAllReminders();
    }

    private Reminder convertToReminder(ReminderItem reminderItem) {
        return new Reminder(
                reminderItem.getHour(),
                reminderItem.getMinute(),
                reminderItem.isAM(),
                reminderItem.getDaysOfWeek(),
                reminderItem.isEnabled()
        );
    }

    private ReminderItem convertToReminderItem(Reminder reminder) {
        ReminderItem reminderItem = new ReminderItem(reminder.getId());
        reminderItem.setHour(reminder.getHour());
        reminderItem.setMinute(reminder.getMinute());
        reminderItem.setAM(reminder.isAM());
        reminderItem.setDaysOfWeek(reminder.getDaysOfWeek());
        reminderItem.setEnabled(reminder.isEnabled());
        return reminderItem;
    }
}
