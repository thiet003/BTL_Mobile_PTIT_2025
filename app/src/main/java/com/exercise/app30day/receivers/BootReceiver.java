package com.exercise.app30day.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BootReceiver extends BroadcastReceiver {

    @Inject
    ReminderRepository reminderRepository;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            LiveData<List<ReminderItem>> remindersLiveData = reminderRepository.getAllReminders();

            Observer<List<ReminderItem>> observer = new Observer<>() {
                @Override
                public void onChanged(List<ReminderItem> reminderItems) {
                    for (ReminderItem reminder : reminderItems) {
                        if (reminder.isEnabled()) {
                            AlarmHelper.scheduleReminder(context, reminder);
                        }
                    }
                    remindersLiveData.removeObserver(this);
                }
            };

            remindersLiveData.observeForever(observer);
        }
    }
}