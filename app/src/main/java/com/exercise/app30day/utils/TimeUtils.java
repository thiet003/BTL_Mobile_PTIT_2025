package com.exercise.app30day.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class TimeUtils {
    public static String convertLongToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    @SuppressLint("DefaultLocale")
    public static String formatMillisecondsToMMSS(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
