package com.exercise.app30day.data.utils;

import androidx.room.TypeConverter;

public class DaysOfWeekConverter {
    @TypeConverter
    public static String fromDaysArray(boolean[] days) {
        if (days == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (boolean day : days) {
            result.append(day ? '1' : '0');
        }
        return result.toString();
    }

    @TypeConverter
    public static boolean[] toDaysArray(String daysString) {
        if (daysString == null) {
            return new boolean[7]; // Default all false
        }

        boolean[] days = new boolean[7];
        for (int i = 0; i < daysString.length() && i < 7; i++) {
            days[i] = daysString.charAt(i) == '1';
        }
        return days;
    }
}