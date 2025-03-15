package com.exercise.app30day.features.splash;

import android.content.Intent;
import android.os.Handler;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivitySplashBinding;
import com.exercise.app30day.features.main.MainActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, NoneViewModel> {
    @Override
    protected void initView() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
        }, 3000);
    }

    @Override
    protected void initListener() {

    }
}