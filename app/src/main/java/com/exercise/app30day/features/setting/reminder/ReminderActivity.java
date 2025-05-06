package com.exercise.app30day.features.setting.reminder;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityReminderBinding;
import com.exercise.app30day.items.ReminderItem;

public class ReminderActivity extends BaseActivity<ActivityReminderBinding, NoneViewModel>
        implements View.OnClickListener, ReminderAdapter.ReminderListener{

    private ReminderAdapter reminderAdapter;
    @Override
    protected void initView() {
        reminderAdapter = new ReminderAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.remindersRecyclerView.setLayoutManager(layoutManager);
        binding.remindersRecyclerView.setAdapter(reminderAdapter);
    }

    @Override
    protected void initListener() {
        binding.backButton.setOnClickListener(this);
        binding.addReminderFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.backButton){
            finish();
        }else if(v == binding.addReminderFab){
            ReminderTimePickerDialog timePickerDialog = new ReminderTimePickerDialog(this, (hour, minute, isAM) -> {
                ReminderItem reminderItem = new ReminderItem(reminderAdapter.getItemCount() + 1);
                reminderItem.setHour(hour);
                reminderItem.setMinute(minute);
                reminderItem.setAM(isAM);
                reminderAdapter.addItem(reminderItem);
            });
            timePickerDialog.show();
        }
    }

    @Override
    public void onReminderToggled(ReminderItem reminder, boolean isEnabled) {
        reminder.setEnabled(isEnabled);
        binding.remindersRecyclerView.post(()-> reminderAdapter.updateItem(reminder));
    }

    @Override
    public void onReminderDeleted(ReminderItem reminder) {
        reminderAdapter.removeItem(reminder);
    }

    @Override
    public void onReminderClicked(ReminderItem reminder) {
        ReminderTimePickerDialog timePickerDialog = new ReminderTimePickerDialog(this, (hour, minute, isAM) -> {
            ReminderItem reminderItem = new ReminderItem(reminderAdapter.getItemCount() + 1);
            reminderItem.setHour(hour);
            reminderItem.setMinute(minute);
            reminderItem.setAM(isAM);
            reminderAdapter.updateItem(reminderItem);
        });
        timePickerDialog.show();
    }

    @Override
    public void onRepeatDaysClicked(ReminderItem reminder) {
        // Handle repeat days click
        ReminderDaySelectorDialog daysPickerDialog = new ReminderDaySelectorDialog(this, reminder.getDaysOfWeek(), selectedDays -> {
            reminder.setDaysOfWeek(selectedDays);
            reminderAdapter.updateItem(reminder);
        });
        daysPickerDialog.show();
    }
}