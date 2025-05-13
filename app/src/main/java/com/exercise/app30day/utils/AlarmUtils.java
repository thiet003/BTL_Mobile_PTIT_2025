package com.exercise.app30day.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.receivers.AlarmReceiver;

import java.util.Calendar;

public final class AlarmUtils {
    public static void scheduleAllReminders(Context context, ReminderItem[] reminders) {
        for (ReminderItem reminder : reminders) {
            if (reminder.isEnabled()) {
                scheduleReminder(context, reminder);
            }
        }
    }
    
    public static void scheduleReminder(Context context, ReminderItem reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;
        
        boolean[] daysOfWeek = reminder.getDaysOfWeek();
        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
            if (daysOfWeek[dayOfWeek]) {
                scheduleReminderForDay(context, alarmManager, reminder, dayOfWeek);
            }
        }
    }
    
    @SuppressLint("ScheduleExactAlarm")
    private static void scheduleReminderForDay(Context context, AlarmManager alarmManager,
                                               ReminderItem reminder, int dayOfWeek) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(IntentKeys.EXTRA_REMINDER_ID, reminder.getId());
        int requestCode = reminder.getId() * 10 + dayOfWeek;
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = createCalender(reminder, dayOfWeek);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    public static void cancelReminder(Context context, ReminderItem reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;
        
        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            int requestCode = reminder.getId() * 10 + dayOfWeek;

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
            );

            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();
            }
        }
    }

    public static void updateReminder(Context context, ReminderItem reminder) {
        cancelReminder(context, reminder);
        if (reminder.isEnabled()) {
            scheduleReminder(context, reminder);
        }
    }

    @NonNull
    private static Calendar createCalender(ReminderItem reminder, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        // Our days: 0 = Sunday, 1 = Monday, ..., 6 = Saturday
        // Calendar days: 1 = Sunday, 2 = Monday, ..., 7 = Saturday
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek + 1);
        calendar.set(Calendar.HOUR, reminder.getHour());
        calendar.set(Calendar.MINUTE, reminder.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, reminder.isAM() ? Calendar.AM : Calendar.PM);
        return calendar;
    }
}