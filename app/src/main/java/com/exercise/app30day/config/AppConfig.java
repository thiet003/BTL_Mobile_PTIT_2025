package com.exercise.app30day.config;

import com.exercise.app30day.keys.DataStoreKeys;
import com.orhanobut.hawk.Hawk;

public final class AppConfig {
    public static final long EXERCISE_PREPARE_DURATION = Hawk.get(DataStoreKeys.PREPARE_DURATION_KEY, 15000L);

    public static final long EXERCISE_REST_DURATION  = Hawk.get(DataStoreKeys.REST_DURATION_KEY, 30000L);


    public static void updateExercisePrepareDuration(long duration){
        Hawk.put(DataStoreKeys.REST_DURATION_KEY, duration);
    }

    public static void updateExerciseRestDuration(long duration){
        Hawk.put(DataStoreKeys.REST_DURATION_KEY, duration);
    }

}
