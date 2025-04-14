package com.exercise.app30day.config;

import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

public final class AppConfig {

    public static final int MAX_WEIGHT = 200;
    public static final int MIN_WEIGHT = 30;
    public static final int MAX_HEIGHT = 250;
    public static final int MIN_HEIGHT = 100;
    public static final long DEFAULT_DELAY_MILLIS = 100L;

    public static long getExercisePrepareDuration(){
        return Hawk.get(HawkKeys.PREPARE_DURATION_KEY, 15000L);
    }

    public static void setExercisePrepareDuration(long duration){
        Hawk.put(HawkKeys.REST_DURATION_KEY, duration);
    }

    public static long getExerciseRestDuration(){
        return Hawk.get(HawkKeys.REST_DURATION_KEY, 30000L);
    }

    public static void setExerciseRestDuration(long duration){
        Hawk.put(HawkKeys.REST_DURATION_KEY, duration);
    }

}
