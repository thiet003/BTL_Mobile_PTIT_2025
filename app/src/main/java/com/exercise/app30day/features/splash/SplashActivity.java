package com.exercise.app30day.features.splash;

import static com.exercise.app30day.utils.IntentKeys.EXTRA_LANGUAGE_CHANGED;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.databinding.ActivitySplashBinding;
import com.exercise.app30day.features.intro.IntroActivity;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.utils.SpeechHelper;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding, NoneViewModel> {

    int countTimeSplash = 0;
    Handler handler = new Handler();

    @Override
    protected void initView() {

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
        SpeechHelper.getInstance().init();
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