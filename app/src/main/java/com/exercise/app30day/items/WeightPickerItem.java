package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class WeightPickerItem extends BaseItem {

    private int value;
    public WeightPickerItem(int id, int value) {
        super(id);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
