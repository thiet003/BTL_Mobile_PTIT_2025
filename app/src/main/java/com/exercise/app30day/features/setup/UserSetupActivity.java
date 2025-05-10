package com.exercise.app30day.features.setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exercise.app30day.config.AppConfig;
import com.shawnlin.numberpicker.NumberPicker;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityUserSetupBinding;
import com.exercise.app30day.features.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class UserSetupActivity extends BaseActivity<ActivityUserSetupBinding, UserSetupViewModel>
        implements View.OnClickListener, NumberPicker.OnValueChangeListener{

    private EditText editTextName;
    private NumberPicker heightPicker;
    private NumberPicker weightPickerWhole;
    private Button buttonSave;

    @Override
    protected void initView() {
        editTextName = binding.editTextName;
        heightPicker = binding.heightPicker;
        weightPickerWhole = binding.weightPickerWhole;
        buttonSave = binding.buttonSave;
        setupNumberPickers();
    }
    
    @SuppressLint("DefaultLocale")
    private void setupNumberPickers() {
        // Setup height picker formatting
        heightPicker.setMaxValue(AppConfig.MAX_HEIGHT);
        heightPicker.setMinValue(AppConfig.MIN_HEIGHT);
        heightPicker.setValue(170);
        heightPicker.setWrapSelectorWheel(true);

        weightPickerWhole.setMaxValue(AppConfig.MAX_WEIGHT);
        weightPickerWhole.setMinValue(AppConfig.MIN_WEIGHT);
        weightPickerWhole.setValue(70);
        weightPickerWhole.setWrapSelectorWheel(true);
    }

    @Override
    protected void initListener() {
        buttonSave.setOnClickListener(this);
        weightPickerWhole.setOnValueChangedListener(this);
    }

    private void saveUserInfo() {
        String name = editTextName.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int height = heightPicker.getValue();
            double weight = weightPickerWhole.getValue();
            
            // Log the weight value
            System.out.println("Cân nặng: " + weight);
            Toast.makeText(this, "Saving profile...", Toast.LENGTH_SHORT).show();

            viewModel.saveUserInfo(name, height, weight);
            // Save in a background thread
            Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
            startMainActivity();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for height and weight", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == binding.buttonSave){
            saveUserInfo();
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(picker == weightPickerWhole){
            System.out.println("Weight whole changed: " + newVal);
        }
    }
}
