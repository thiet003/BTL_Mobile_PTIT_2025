package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class UserItem extends BaseItem {
    private String name;
    private double weight;
    private double height;
    private long date;
    public UserItem(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
