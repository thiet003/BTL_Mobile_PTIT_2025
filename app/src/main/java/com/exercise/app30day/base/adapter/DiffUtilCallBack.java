package com.exercise.app30day.base.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffUtilCallBack<T extends BaseItem> extends DiffUtil.Callback {

    private List<T> oldData;
    private List<T> newData;

    public DiffUtilCallBack(List<T> oldData, List<T> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).getId() == newData.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).equals(newData.get(newItemPosition));
    }
}
