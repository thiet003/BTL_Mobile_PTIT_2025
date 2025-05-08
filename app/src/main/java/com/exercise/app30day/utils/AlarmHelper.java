package com.exercise.app30day.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.receivers.AlarmReceiver;

import java.util.Calendar;

public class AlarmHelper {
    private static final String TAG = "AlarmHelper";

    /**
     * Schedule all reminders from the database
     */
    public static void scheduleAllReminders(Context context, ReminderItem[] reminders) {
        for (ReminderItem reminder : reminders) {
            if (reminder.isEnabled()) {
                scheduleReminder(context, reminder);
            }
        }
    }

    /**
     * Schedule a single reminder
     */
    public static void scheduleReminder(Context context, ReminderItem reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        // For each enabled day of the week, schedule an alarm
        boolean[] daysOfWeek = reminder.getDaysOfWeek();
        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
            if (daysOfWeek[dayOfWeek]) {
                scheduleReminderForDay(context, alarmManager, reminder, dayOfWeek);
            }
        }
    }

    /**
     * Schedule a reminder for a specific day of the week
     */
    @SuppressLint("ScheduleExactAlarm")
    private static void scheduleReminderForDay(Context context, AlarmManager alarmManager,
                                               ReminderItem reminder, int dayOfWeek) {
        // Create intent for the alarm
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("reminderId", reminder.getId());
        // Use a unique request code based on reminder ID and day of week
        int requestCode = reminder.getId() * 10 + dayOfWeek;

        // Create the pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Calculate the time for the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, convertToCalendarDay(dayOfWeek));
        calendar.set(Calendar.HOUR, reminder.getHour());
        calendar.set(Calendar.MINUTE, reminder.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, reminder.isAM() ? Calendar.AM : Calendar.PM);

        // If the time is in the past, add one week
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        // Schedule the alarm
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    /**
     * Cancel a reminder by removing all its alarms
     */
    public static void cancelReminder(Context context, ReminderItem reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        // Cancel alarms for all days of the week
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

    /**
     * Update a reminder by canceling and rescheduling it
     */
    public static void updateReminder(Context context, ReminderItem reminder) {
        cancelReminder(context, reminder);
        if (reminder.isEnabled()) {
            scheduleReminder(context, reminder);
        }
    }

    /**
     * Convert our day of week index (0 = Sunday) to Calendar.DAY_OF_WEEK values
     */
    private static int convertToCalendarDay(int dayOfWeek) {
        // Our days: 0 = Sunday, 1 = Monday, ..., 6 = Saturday
        // Calendar days: 1 = Sunday, 2 = Monday, ..., 7 = Saturday
        return dayOfWeek + 1;
    }
}