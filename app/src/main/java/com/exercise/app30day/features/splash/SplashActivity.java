package com.exercise.app30day.features.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.databinding.ActivitySplashBinding;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding, NoneViewModel> {

    int countTimeSplash = 0;
    Handler handler = new Handler();

    @Override
    protected void initView() {
        if(!Hawk.get(HawkKeys.DATABASE_INITIALIZED, false)) {
            AppDatabase.getInstance(this).initializeData(() -> Hawk.put(HawkKeys.DATABASE_INITIALIZED, true));
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countTimeSplash++;
                if (countTimeSplash > 3 && Hawk.get(HawkKeys.DATABASE_INITIALIZED, false)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}