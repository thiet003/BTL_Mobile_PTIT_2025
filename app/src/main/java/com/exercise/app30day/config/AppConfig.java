package com.exercise.app30day.config;

import androidx.collection.PackingUtilsKt;

import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

public final class AppConfig {

    public static final int MAX_WEIGHT = 200;
    public static final int MIN_WEIGHT = 30;
    public static final int MAX_HEIGHT = 250;
    public static int MIN_HEIGHT = 100;
    public static final long DEFAULT_DELAY_MILLIS = 100L;

    public static long getExercisePrepareDuration(){
        return Hawk.get(HawkKeys.PREPARE_DURATION_KEY, 15000L);
    }

    public static void setExercisePrepareDuration(long duration){
        Hawk.put(HawkKeys.PREPARE_DURATION_KEY, duration);
    }

    public static long getExerciseRestDuration(){
        return Hawk.get(HawkKeys.REST_DURATION_KEY, 30000L);
    }

    public static void setExerciseRestDuration(long duration){
        Hawk.put(HawkKeys.REST_DURATION_KEY, duration);
    }

    public static boolean isPlayBackgroundMusic(){
        return Hawk.get(HawkKeys.MUSIC_ENABLED_KEY, true);
    }

    public static void setPlayBackgroundMusic(boolean play){
        Hawk.put(HawkKeys.MUSIC_ENABLED_KEY, play);
    }

    public static float getBackgroundMusicVolume(){
        return Hawk.get(HawkKeys.MUSIC_VOLUME_KEY, 0.5f);
    }

    public static void setBackgroundMusicVolume(float volume){
        Hawk.put(HawkKeys.MUSIC_VOLUME_KEY, volume);
    }

    public static boolean isVoiceEnabled(){
        return Hawk.get(HawkKeys.VOICE_ENABLE_KEY, true);
    }

    public static void setVoiceEnabled(boolean isEnabled){
        Hawk.put(HawkKeys.VOICE_ENABLE_KEY, isEnabled);
    }

    public static void setVoiceSpeed(float speed){
        if(speed < 0.5f || speed > 2.0f){
            throw new IllegalArgumentException("Speed must be between 0.5 and 2.0");
        }
        Hawk.put(HawkKeys.VOICE_SPEED_KEY, speed);
    }

    public static float getVoiceSpeed(){
        return Hawk.get(HawkKeys.VOICE_SPEED_KEY, 1.0f);
    }

    public static void setVoicePitch(float pitch){
        if(pitch < 0.5f || pitch > 2.0f){
            throw new IllegalArgumentException("Pitch must be between 0.5 and 2.0");
        }
        Hawk.put(HawkKeys.VOICE_PITCH_KEY, pitch);
    }

    public static float getVoicePitch(){
        return Hawk.get(HawkKeys.VOICE_PITCH_KEY, 1.0f);
    }

}
