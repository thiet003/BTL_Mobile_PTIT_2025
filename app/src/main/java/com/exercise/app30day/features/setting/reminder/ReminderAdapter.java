package com.exercise.app30day.features.setting.reminder;

import android.view.View;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemReminderBinding;
import com.exercise.app30day.items.ReminderItem;

public class ReminderAdapter extends BaseRecyclerViewAdapter<ReminderItem, ItemReminderBinding>{
    private ReminderListener reminderListener;

    public interface ReminderListener {
        void onReminderToggled(ReminderItem reminder, boolean isEnabled);
        void onReminderDeleted(ReminderItem reminder);
        void onReminderClicked(ReminderItem reminder);
        void onRepeatDaysClicked(ReminderItem reminder);
    }

    public ReminderAdapter(ReminderListener reminderListener){
        this.reminderListener = reminderListener;
        setOnItemClickListener((data, position) -> reminderListener.onReminderClicked(data));
    }

    public void setReminderListener(ReminderListener reminderListener) {
        this.reminderListener = reminderListener;
        setOnItemClickListener((data, position) -> reminderListener.onReminderClicked(data));
    }

    @Override
    protected void bindData(ItemReminderBinding binding, ReminderItem item, int position) {
        binding.reminderTimeTextView.setText(item.getFormattedTime());
        binding.repeatDaysTextView.setText(item.getFormattedDays());
        binding.reminderSwitch.setChecked(item.isEnabled());
        binding.reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setEnabled(isChecked);
            reminderListener.onReminderToggled(item, isChecked);
        });

        binding.deleteReminderButton.setOnClickListener(v -> {
            reminderListener.onReminderDeleted(item);
        });

        binding.repeatDaysTextView.setOnClickListener(v -> {
            reminderListener.onRepeatDaysClicked(item);
        });
    }
}
