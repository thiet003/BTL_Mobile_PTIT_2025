package com.exercise.app30day.features.setting.reminder;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ReminderViewModel extends ViewModel {
    private final ReminderRepository reminderRepository;

    private final Context context;

    @Inject
    public ReminderViewModel(ReminderRepository reminderRepository, @ApplicationContext Context context) {
        this.reminderRepository = reminderRepository;
        this.context = context;
    }

    public void insertReminder(ReminderItem reminderItem) {
        reminderRepository.insertReminder(convertToReminder(reminderItem));
        AlarmHelper.scheduleReminder(context, reminderItem);
    }

    public void updateReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.updateReminder(reminder);
        AlarmHelper.updateReminder(context, reminderItem);
    }

    public void deleteReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.deleteReminder(reminder);
        AlarmHelper.cancelReminder(context, reminderItem);
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
