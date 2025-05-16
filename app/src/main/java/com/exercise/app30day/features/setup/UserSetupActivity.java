package com.exercise.app30day.features.setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityUserSetupBinding;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;
import com.shawnlin.numberpicker.NumberPicker;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class UserSetupActivity extends BaseActivity<ActivityUserSetupBinding, UserSetupViewModel> {

    private EditText editTextName;
    private NumberPicker heightPicker;
    private NumberPicker weightPickerWhole;
    private Button buttonSave;

    String[] displayedWeights;

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

        int weightRange = (AppConfig.MAX_WEIGHT - AppConfig.MIN_WEIGHT) * 10 + 1;
        displayedWeights = new String[weightRange];
        weightPickerWhole.setMinValue(0);
        weightPickerWhole.setMaxValue(weightRange - 1);
        for (int i = 0; i < weightRange; i++) {
            float weight = AppConfig.MIN_WEIGHT + i * 0.1f;
            if(weight == 70.0){
                weightPickerWhole.setValue(i);
            }
            displayedWeights[i] = String.format("%.1f", weight);
        }
        weightPickerWhole.setDisplayedValues(displayedWeights);
        weightPickerWhole.setWrapSelectorWheel(true);
    }

    @Override
    protected void initListener() {
        buttonSave.setOnClickListener(v -> saveUserInfo());
    }

    private void saveUserInfo() {
        String name = editTextName.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.please_enter_your_name, Toast.LENGTH_SHORT).show();
            return;
        }
        double height = heightPicker.getValue();
        double weight = Double.parseDouble(displayedWeights[weightPickerWhole.getValue()]);
        viewModel.saveUserInfo(name, height, weight);
        Hawk.put(HawkKeys.PROFILE_SETUP_KEY, true);
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
