package com.exercise.app30day.features.setting.reminder;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityReminderBinding;
import com.exercise.app30day.items.ReminderItem;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReminderActivity extends BaseActivity<ActivityReminderBinding, ReminderViewModel>
        implements View.OnClickListener, ReminderAdapter.ReminderListener{

    private ReminderAdapter reminderAdapter;
    @Override
    protected void initView() {
        reminderAdapter = new ReminderAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.remindersRecyclerView.setLayoutManager(layoutManager);
        binding.remindersRecyclerView.setAdapter(reminderAdapter);
        viewModel.getAllReminders().observe(this, reminderItems -> {
            reminderAdapter.setData(reminderItems);
        });
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
                ReminderItem reminderItem = new ReminderItem(0);
                reminderItem.setHour(hour);
                reminderItem.setMinute(minute);
                reminderItem.setAM(isAM);
                viewModel.insertReminder(reminderItem);
            });
            timePickerDialog.setValues(6, 0, false);
            timePickerDialog.show();
        }
    }

    @Override
    public void onReminderToggled(ReminderItem reminder, int position, boolean isEnabled) {
        reminder.setEnabled(isEnabled);
        viewModel.updateReminder(reminder);
    }

    @Override
    public void onReminderDeleted(ReminderItem reminder, int position) {
        viewModel.deleteReminder(reminder);
    }

    @Override
    public void onReminderClicked(ReminderItem reminder, int position) {
        ReminderTimePickerDialog timePickerDialog = new ReminderTimePickerDialog(this, (hour, minute, isAM) -> {
            reminder.setHour(hour);
            reminder.setMinute(minute);
            reminder.setAM(isAM);
            viewModel.updateReminder(reminder);
        });
        timePickerDialog.setValues(reminder.getHour(), reminder.getMinute(), reminder.isAM());
        timePickerDialog.show();
    }

    @Override
    public void onRepeatDaysClicked(ReminderItem reminder, int position) {
        // Handle repeat days click
        ReminderDaySelectorDialog daysPickerDialog = new ReminderDaySelectorDialog(this, reminder.getDaysOfWeek(), selectedDays -> {
            reminder.setDaysOfWeek(selectedDays);
            viewModel.updateReminder(reminder);
        });
        daysPickerDialog.show();
    }
}