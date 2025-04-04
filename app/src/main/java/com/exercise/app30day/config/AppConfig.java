package com.exercise.app30day.config;

import com.exercise.app30day.keys.DataStoreKeys;
import com.orhanobut.hawk.Hawk;

public final class AppConfig {

    public static long getExercisePrepareDuration(){
        return Hawk.get(DataStoreKeys.PREPARE_DURATION_KEY, 15000L);
    }

    public static void setExercisePrepareDuration(long duration){
        Hawk.put(DataStoreKeys.REST_DURATION_KEY, duration);
    }

    public static long getExerciseRestDuration(){
        return Hawk.get(DataStoreKeys.REST_DURATION_KEY, 30000L);
    }

    public static void setExerciseRestDuration(long duration){
        Hawk.put(DataStoreKeys.REST_DURATION_KEY, duration);
    }

}
