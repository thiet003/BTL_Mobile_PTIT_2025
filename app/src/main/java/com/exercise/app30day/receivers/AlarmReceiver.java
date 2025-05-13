package com.exercise.app30day.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.exercise.app30day.R;
import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmUtils;
import com.exercise.app30day.utils.HawkKeys;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.NotificationUtils;
import com.exercise.app30day.utils.ResourceUtils;
import com.orhanobut.hawk.Hawk;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmReceiver extends BroadcastReceiver {

    @Inject
    CourseRepository courseRepository;

    @Inject
    ReminderRepository reminderRepository;

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(Context context, Intent intent) {
        int reminderId = intent.getIntExtra(IntentKeys.EXTRA_REMINDER_ID, -1);
        if(reminderId != -1) {
            new Thread(()->{
                CourseItem courseItem = courseRepository.getCurrentCourseItemSync();
                ReminderItem reminder = reminderRepository.getReminderByIdSync(reminderId);
                if(courseItem != null && reminder != null && reminder.isEnabled()) {
                    mainHandler.post(()->{
                        if (shouldTriggerToday(reminder)) {
                            showNotification(context, reminder, courseItem);
                        }

                        AlarmUtils.updateReminder(context, reminder);
                    });
                }
            }).start();
        }
    }

    private boolean shouldTriggerToday(ReminderItem reminder) {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Convert to 0-based index
        return reminder.getDaysOfWeek()[today];
    }

    private void showNotification(Context context, ReminderItem reminder, CourseItem courseItem) {
        String title;
        String message;
        String bigText;

        User user = Hawk.get(HawkKeys.INSTANCE_USER_KEY);
        if(user != null){
            title = context.getString(R.string.workout_title, user.getName());
        }else{
            title = context.getString(R.string.workout_title_second);
        }

        int currentDay = courseItem.getNumberOfCompletedDays() + 1;

        if (courseItem.getNumberOfCompletedDays() == 0) {
            message = context.getString(R.string.start_course_message, courseItem.getName(), currentDay);
            bigText = context.getString(R.string.start_course_big_text, courseItem.getName());
        } else {
            message = context.getString(R.string.continue_course_message, courseItem.getName(), currentDay);
            bigText = context.getString(R.string.continue_course_big_text,
                    courseItem.getNumberOfCompletedDays(),
                    courseItem.getNumberOfDays());
        }

        NotificationUtils.NotificationItem notificationItem = new NotificationUtils.NotificationItem(
                reminder.getId(),
                title,
                message,
                bigText,
                ResourceUtils.getDrawableId(context, courseItem.getImage())
        );
        NotificationUtils.showReminderNotification(
                context,
                notificationItem
        );
    }
}