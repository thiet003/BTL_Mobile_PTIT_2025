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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.exercise.app30day.R;

public class VoiceSelectionDialog extends Dialog {

    public interface OnVoiceSelectedListener {
        void onVoiceSelected(boolean isFemale);
    }

    private final Context context;
    private final boolean isInitiallyFemale;
    private OnVoiceSelectedListener listener;

    private RadioGroup radioGroupVoice;
    private RadioButton radioFemale;
    private RadioButton radioMale;
    private Button btnCancel;
    private Button btnConfirm;

    public VoiceSelectionDialog(@NonNull Context context, boolean isInitiallyFemale) {
        super(context);
        this.context = context;
        this.isInitiallyFemale = isInitiallyFemale;
    }

    public void setOnVoiceSelectedListener(OnVoiceSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_voice_selection);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }

        // Initialize views
        radioGroupVoice = findViewById(R.id.radioGroupVoice);
        radioFemale = findViewById(R.id.radioFemale);
        radioMale = findViewById(R.id.radioMale);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Set initial selection
        if (isInitiallyFemale) {
            radioFemale.setChecked(true);
        } else {
            radioMale.setChecked(true);
        }

        // Set up button listeners
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    boolean isFemale = radioFemale.isChecked();
                    listener.onVoiceSelected(isFemale);
                }
                dismiss();
            }
        });
    }
}