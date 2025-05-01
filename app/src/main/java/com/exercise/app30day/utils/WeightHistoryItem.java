package com.exercise.app30day.utils;

import com.exercise.app30day.base.adapter.BaseItem;

public class WeightHistoryItem extends BaseItem {

    private double weight;

    private long date;
    public WeightHistoryItem(int id) {
        super(id);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
