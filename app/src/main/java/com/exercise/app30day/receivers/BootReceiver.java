package com.exercise.app30day.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BootReceiver extends BroadcastReceiver {

    @Inject
    ReminderRepository reminderRepository;

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            new Thread(()->{
                List<ReminderItem> reminders = reminderRepository.getAllRemindersSync();
                if(reminders != null){
                    for (ReminderItem reminder : reminders) {
                        if (reminder.isEnabled()) {
                            mainHandler.post(()->{
                                AlarmUtils.scheduleReminder(context, reminder);
                            });
                        }
                    }
                }
            }).start();
        }
    }
}