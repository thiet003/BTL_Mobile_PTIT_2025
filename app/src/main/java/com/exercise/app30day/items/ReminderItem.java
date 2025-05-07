package com.exercise.app30day.items;

import android.annotation.SuppressLint;

import com.exercise.app30day.base.adapter.BaseItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.Arrays;

public class ReminderItem extends BaseItem {

    private int hour;
    private int minute;
    private boolean isAM;
    private boolean[] daysOfWeek; // Sunday = 0, Monday = 1, ..., Saturday = 6
    private boolean isEnabled;
    public ReminderItem(int id) {
        super(id);
        daysOfWeek = new boolean[7];
        Arrays.fill(daysOfWeek, true);
        isEnabled = true;
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

    @SuppressLint("DefaultLocale")
    public String getFormattedTime() {
        int hour24 = hour % 12;
        if (!isAM) hour24 += 12;
        return String.format("%02d:%02d", hour24, minute);
    }

    public String getFormattedDays() {
        String[] dayNames = TimeUtils.getWeekdayNames();
        StringBuilder daysStr = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            if (daysOfWeek[i]) {
                if (daysStr.length() > 0) {
                    daysStr.append(", ");
                }
                daysStr.append(dayNames[i]);
            }
        }

        return daysStr.toString();
    }


}
