package com.exercise.app30day.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class TimeUtils {
    public static String convertMillisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", new Locale("vi", "VN"));
        return sdf.format(new Date(millis));
    }

    @SuppressLint("DefaultLocale")
    public static String formatMillisecondsToMMSS(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
