package com.exercise.app30day.features.setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.databinding.ActivityIntroBinding;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;
public class UserSetupActivity extends BaseActivity<ActivityIntroBinding, NoneViewModel> {

    private EditText editTextName;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Button buttonSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);
        
        editTextName = findViewById(R.id.editTextName);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonSave = findViewById(R.id.buttonSave);
        
        buttonSave.setOnClickListener(v -> saveUserInfo());
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
    }

    private void saveUserInfo() {
        String name = editTextName.getText().toString().trim();
        String heightStr = editTextHeight.getText().toString().trim();
        String weightStr = editTextWeight.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(heightStr)) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(weightStr)) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            User user = new User(0, name, height);
            
            Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);
            
            // Save user to database
            new Thread(() -> {
                long userId = AppDatabase.getInstance(this).userDao().insertUser(user);
                
                WeightHistory weightHistory = new WeightHistory(weight, System.currentTimeMillis());
                AppDatabase.getInstance(this).weightHistoryDao().insertWeightHistory(weightHistory);
                
                Hawk.put(HawkKeys.PROFILE_SETUP_KEY, true);
                
                // Start MainActivity
                runOnUiThread(this::startMainActivity);
            }).start();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for height and weight", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
