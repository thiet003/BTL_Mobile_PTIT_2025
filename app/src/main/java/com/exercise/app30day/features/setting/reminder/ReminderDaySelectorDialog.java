package com.exercise.app30day.features.setting.reminder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.exercise.app30day.R;
import com.exercise.app30day.databinding.DialogReminderDaySelectorBinding;
import com.exercise.app30day.utils.ScreenUtils;

public class ReminderDaySelectorDialog extends Dialog implements View.OnClickListener {

    private DialogReminderDaySelectorBinding binding;

    private boolean[] daysOfWeek;

    private CheckBox[] dayCheckBoxes;

    private Context context;

    private OnDaysSelectedListener onDaysSelectedListener;

    public interface OnDaysSelectedListener {
        void onDaysSelected(boolean[] daysOfWeek);
    }
    public ReminderDaySelectorDialog(@NonNull Context context, boolean[] daysOfWeek, OnDaysSelectedListener onDaysSelectedListener) {
        super(context);
        this.context = context;
        this.daysOfWeek = daysOfWeek;
        this.onDaysSelectedListener = onDaysSelectedListener;
    }

    public void setOnDaysSelectedListener(OnDaysSelectedListener onDaysSelectedListener) {
        this.onDaysSelectedListener = onDaysSelectedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogReminderDaySelectorBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        dayCheckBoxes = new CheckBox[7];

        dayCheckBoxes[0] = binding.sundayCheckBox;
        dayCheckBoxes[1] = binding.mondayCheckBox;
        dayCheckBoxes[2] = binding.tuesdayCheckBox;
        dayCheckBoxes[3] = binding.wednesdayCheckBox;
        dayCheckBoxes[4] = binding.thursdayCheckBox;
        dayCheckBoxes[5] = binding.fridayCheckBox;
        dayCheckBoxes[6] = binding.saturdayCheckBox;

        for (int i = 0; i < 7; i++) {
            dayCheckBoxes[i].setChecked(daysOfWeek[i]);
        }

        binding.cancelButton.setOnClickListener(this);
        binding.okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.cancelButton){
            dismiss();
        }else if(v == binding.okButton){
            handleSaveDays();
        }
    }

    private void handleSaveDays() {
        boolean[] newDaysOfWeek = new boolean[7];
        for (int i = 0; i < 7; i++) {
            newDaysOfWeek[i] = dayCheckBoxes[i].isChecked();
        }
        boolean atLeastOneDaySelected = false;
        for (boolean day : newDaysOfWeek) {
            if (day) {
                atLeastOneDaySelected = true;
                break;
            }
        }

        if (!atLeastOneDaySelected) {
            Toast.makeText(context, R.string.please_select_at_least_one_day, Toast.LENGTH_SHORT).show();
            return;
        }

        if (onDaysSelectedListener != null) {
            onDaysSelectedListener.onDaysSelected(newDaysOfWeek);
        }

        dismiss();
    }
}
