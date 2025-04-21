package com.exercise.app30day.base.adapter;

import java.io.Serializable;

public abstract class BaseItem implements Serializable {
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
