package com.exercise.app30day.utils;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.exercise.app30day.R;
import com.exercise.app30day.features.main.MainActivity;

public final class NotificationUtils {
    public static final String CHANNEL_ID = "exercise_reminder_channel";
    public static final String CHANNEL_NAME = "Exercise Reminders";
    public static final String CHANNEL_DESCRIPTION = "Notifications for exercise reminders";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showReminderNotification(Context context, NotificationItem item) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                item.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), item.getLargeIcon());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(item.getTitle())
                .setLargeIcon(largeIcon)
                .setContentText(item.getMessage()).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(item.getBigText()))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(item.getId(), builder.build());
    }

    public static class NotificationItem {
        private int id;
        private String title;
        private String message;
        private String bigText;
        private @DrawableRes int largeIcon;

        public NotificationItem(int id, String title, String message, String bigText, int largeIcon) {
            this.id = id;
            this.title = title;
            this.message = message;
            this.bigText = bigText;
            this.largeIcon = largeIcon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getBigText() {
            return bigText;
        }

        public void setBigText(String bigText) {
            this.bigText = bigText;
        }

        public int getLargeIcon() {
            return largeIcon;
        }

        public void setLargeIcon(int largeIcon) {
            this.largeIcon = largeIcon;
        }
    }
}