package com.exercise.app30day;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application {

    public static MainApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Hawk.init(this).build();
    }
}
