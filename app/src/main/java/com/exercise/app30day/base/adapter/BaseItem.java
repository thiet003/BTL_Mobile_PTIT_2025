package com.exercise.app30day.base.adapter;

public abstract class BaseItem {
    private int id;

    public BaseItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
