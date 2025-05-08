package com.exercise.app30day.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmHelper;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.NotificationHelper;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmReceiver extends BroadcastReceiver {

    @Inject
    ReminderRepository reminderRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        int reminderId = intent.getIntExtra(IntentKeys.EXTRA_REMINDER_ID, -1);
        if (reminderId != -1) {
            LiveData<List<ReminderItem>> remindersLiveData = reminderRepository.getAllReminders();
            Observer<List<ReminderItem>> observer = new Observer<>() {
                @Override
                public void onChanged(List<ReminderItem> reminderItems) {
                    for (ReminderItem reminder : reminderItems) {
                        if (reminder.getId() == reminderId && reminder.isEnabled()) {
                            if (shouldTriggerToday(reminder)) {
                                showNotification(context, reminder);
                            }

                            AlarmHelper.updateReminder(context, reminder);
                        }
                    }

                    // Remove the observer after handling
                    remindersLiveData.removeObserver(this);
                }
            };

            remindersLiveData.observeForever(observer);
        }
    }

    private boolean shouldTriggerToday(ReminderItem reminder) {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Convert to 0-based index
        return reminder.getDaysOfWeek()[today];
    }

    private void showNotification(Context context, ReminderItem reminder) {
        String title = "It's time for your workout";
        String message = "Your exercise reminder " + reminder.getFormattedTime();

        NotificationHelper.showReminderNotification(
                context,
                reminder.getId(),
                title,
                message
        );
    }
}