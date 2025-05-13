package com.exercise.app30day.features.splash;

import static com.exercise.app30day.utils.IntentKeys.EXTRA_LANGUAGE_CHANGED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.databinding.ActivitySplashBinding;
import com.exercise.app30day.features.intro.IntroActivity;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.features.setup.UserSetupActivity;
import com.exercise.app30day.utils.SpeechHelper;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding, NoneViewModel> {

    int countTimeSplash = 0;
    Handler handler = new Handler();

    @Override
    protected void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1000);
            }else{
                loadSplash();
            }
        }else{
            loadSplash();
        }

        SpeechHelper.getInstance().init();
    }

    @Override
    protected void initListener() {

    }

    private void loadSplash() {
        boolean languageChanged = getIntent().getBooleanExtra(EXTRA_LANGUAGE_CHANGED, false);

        if(languageChanged){
            AppDatabase.updateLanguage(this, ()->{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        countTimeSplash++;
                        if (countTimeSplash > 3) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }else{
                            handler.postDelayed(this, 1000);
                        }
                    }
                }, 1000);
            });
        }else{
            if(!AppDatabase.isDataInitialized()) {
                AppDatabase.initializeData(this);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    countTimeSplash++;
                    if (countTimeSplash > 3 && AppDatabase.isDataInitialized()) {
                        Intent intent;
                        if(!Hawk.get(HawkKeys.INTRO_SHOWN_KEY, false)){
                            intent = new Intent(SplashActivity.this, IntroActivity.class);
                        }else{
                            intent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }else{
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            loadSplash();
        }
    }
}