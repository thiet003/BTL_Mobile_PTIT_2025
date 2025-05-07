package com.exercise.app30day.data.utils;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromBooleansToString(boolean[] booleans) {
        if (booleans == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (boolean day : booleans) {
            result.append(day ? '1' : '0');
        }
        return result.toString();
    }

    @TypeConverter
    public static boolean[] fromStringToBooleans(String string) {
        if (string == null) {
            return new boolean[7];
        }
        boolean[] days = new boolean[string.length()];
        for (int i = 0; i < string.length(); i++) {
            days[i] = string.charAt(i) == '1';
        }
        return days;
    }
}