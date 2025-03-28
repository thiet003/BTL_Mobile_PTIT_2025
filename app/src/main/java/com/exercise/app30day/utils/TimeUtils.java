package com.exercise.app30day.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {
    public static String convertLongToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

}
