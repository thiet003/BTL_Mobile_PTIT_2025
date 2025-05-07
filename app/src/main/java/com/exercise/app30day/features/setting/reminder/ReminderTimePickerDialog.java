package com.exercise.app30day.features.setting.reminder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.exercise.app30day.databinding.DialogReminderTimePickerBinding;
import com.exercise.app30day.utils.ScreenUtils;
import com.shawnlin.numberpicker.NumberPicker;

public class ReminderTimePickerDialog extends Dialog implements View.OnClickListener {

    private DialogReminderTimePickerBinding binding;

    private NumberPicker hourPicker, minutePicker, amPmPicker;

    private int hour = 10, minutes = 0;

    private boolean isAM = true;

    private OnTimePickerListener  onTimePickerListener;

    public interface OnTimePickerListener {
        void onTimePicker(int hour, int minute, boolean isAM);
    }
    @SuppressLint("DefaultLocale")
    public ReminderTimePickerDialog(@NonNull Context context, OnTimePickerListener onTimePickerListener) {
        super(context);
        this.onTimePickerListener = onTimePickerListener;
    }

    public void setOnTimePickerListener(OnTimePickerListener onTimePickerListener) {
        this.onTimePickerListener = onTimePickerListener;
    }

    public void setValues(int hour, int minute, boolean isAM) {
        this.hour = hour;
        this.minutes = minute;
        this.isAM = isAM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogReminderTimePickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }

        hourPicker = binding.hourPicker;
        minutePicker = binding.minutePicker;
        amPmPicker = binding.amPmPicker;

        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setFormatter(value -> String.format("%02d", value));

        amPmPicker.setMinValue(0);
        amPmPicker.setMaxValue(1);
        amPmPicker.setDisplayedValues(new String[]{"AM", "PM"});

        hourPicker.setValue(hour);
        minutePicker.setValue(minutes);
        amPmPicker.setValue(isAM ? 0 : 1);

        binding.doneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.doneButton){
            int hour = hourPicker.getValue();
            int minute = minutePicker.getValue();
            boolean isAM = amPmPicker.getValue() == 0;

            if(onTimePickerListener != null){
                onTimePickerListener.onTimePicker(hour, minute, isAM);
            }

            dismiss();
        }
    }
}
