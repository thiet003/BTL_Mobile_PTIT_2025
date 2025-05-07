package com.exercise.app30day.features.setting.reminder;

import android.view.View;

import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemReminderBinding;
import com.exercise.app30day.items.ReminderItem;

public class ReminderAdapter extends BaseRecyclerViewAdapter<ReminderItem, ItemReminderBinding>{
    private ReminderListener reminderListener;

    public interface ReminderListener {
        void onReminderToggled(ReminderItem reminder, int position, boolean isEnabled);
        void onReminderDeleted(ReminderItem reminder, int position);
        void onReminderClicked(ReminderItem reminder, int position);
        void onRepeatDaysClicked(ReminderItem reminder, int position);
    }

    public ReminderAdapter(ReminderListener reminderListener){
        this.reminderListener = reminderListener;
    }

    public void setReminderListener(ReminderListener reminderListener) {
        this.reminderListener = reminderListener;
    }

    @Override
    protected void bindData(ItemReminderBinding binding, ReminderItem item, int position) {
        binding.reminderTimeTextView.setText(item.getFormattedTime());
        binding.repeatDaysTextView.setText(item.getFormattedDays());
        binding.reminderSwitch.setChecked(item.isEnabled());
        binding.reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(reminderListener != null) reminderListener.onReminderToggled(item, position, isChecked);
        });

        binding.deleteReminderButton.setOnClickListener(v -> {
            if(reminderListener != null) reminderListener.onReminderDeleted(item, position);
        });

        binding.repeatDaysTextView.setOnClickListener(v -> {
            if(reminderListener != null) reminderListener.onRepeatDaysClicked(item, position);
        });
        setOnItemClickListener((data, pos) -> {
            if(reminderListener != null) reminderListener.onReminderClicked(data, pos);
        });
    }
}
