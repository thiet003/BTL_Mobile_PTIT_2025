package com.exercise.app30day.features.setting.workout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.exercise.app30day.R;
import com.exercise.app30day.utils.ScreenUtils;
import com.shawnlin.numberpicker.NumberPicker;

public class TimePickerDialog extends Dialog {

    public interface OnTimeSelectedListener {
        void onTimeSelected(int seconds);
    }

    private final Context context;
    private final String title;
    private int initialSeconds;
    private OnTimeSelectedListener listener;

    private TextView tvTitle;
    private NumberPicker secondPicker;
    private Button btnCancel;
    private Button btnConfirm;

    public TimePickerDialog(@NonNull Context context, String title, int initialSeconds) {
        super(context);
        this.context = context;
        this.title = title;
        this.initialSeconds = initialSeconds;
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_time_picker);

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

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        secondPicker = findViewById(R.id.secondsPicker);

        // Set dialog title
        tvTitle.setText(title);

        // Configure second picker
        secondPicker.setMinValue(10);
        secondPicker.setMaxValue(60);
        secondPicker.setValue(initialSeconds);

        // Additional customization options available with this library
        secondPicker.setFadingEdgeEnabled(true);
        secondPicker.setWrapSelectorWheel(true);

        // Set up button listeners
        btnCancel.setOnClickListener(v -> dismiss());

        btnConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTimeSelected(secondPicker.getValue());
            }
            dismiss();
        });
    }
}