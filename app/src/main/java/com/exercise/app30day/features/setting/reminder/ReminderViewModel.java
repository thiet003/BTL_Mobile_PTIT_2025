package com.exercise.app30day.features.setting.reminder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ReminderViewModel extends ViewModel {
    private final ReminderRepository reminderRepository;

    private final Context context;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Inject
    public ReminderViewModel(ReminderRepository reminderRepository, @ApplicationContext Context context) {
        this.reminderRepository = reminderRepository;
        this.context = context;
    }

    public void insertReminder(ReminderItem reminderItem) {
        new Thread(()->{
            Reminder reminder = convertToReminder(reminderItem);
            long reminderId = reminderRepository.insertReminderSync(reminder);
            reminderItem.setId((int) reminderId);
            mainHandler.post(()-> AlarmUtils.scheduleReminder(context, reminderItem));
        }).start();
    }

    public void updateReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.updateReminder(reminder);
        AlarmUtils.updateReminder(context, reminderItem);
    }

    public void deleteReminder(ReminderItem reminderItem) {
        Reminder reminder = convertToReminder(reminderItem);
        reminder.setId(reminderItem.getId());
        reminderRepository.deleteReminder(reminder);
        AlarmUtils.cancelReminder(context, reminderItem);
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
