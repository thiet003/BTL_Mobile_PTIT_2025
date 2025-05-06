package com.exercise.app30day.data.models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.exercise.app30day.data.utils.Converters;

@Entity(tableName = "reminder")
public class Reminder extends BaseEntity{
    private int hour;
    private int minute;
    private boolean isAM;
    @TypeConverters(Converters.class)
    private boolean[] daysOfWeek;
    private boolean isEnabled;

    public Reminder(int hour, int minute, boolean isAM, boolean[] daysOfWeek, boolean isEnabled) {
        this.hour = hour;
        this.minute = minute;
        this.isAM = isAM;
        this.daysOfWeek = daysOfWeek;
        this.isEnabled = isEnabled;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isAM() {
        return isAM;
    }

    public void setAM(boolean AM) {
        isAM = AM;
    }

    public boolean[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(boolean[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
