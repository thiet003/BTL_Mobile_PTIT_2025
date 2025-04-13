package com.exercise.app30day.features.complete;

import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemWeightPickerBinding;
import com.exercise.app30day.items.WeightPickerItem;

public class WeightPickerAdapter extends BaseRecyclerViewAdapter<WeightPickerItem, ItemWeightPickerBinding>{
    @Override
    protected void bindData(ItemWeightPickerBinding binding, WeightPickerItem item, int position) {
        binding.weightPickerItem.setText(String.valueOf(item.getValue()));
    }
}
