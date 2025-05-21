package com.exercise.app30day.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.Toast;

import com.exercise.app30day.MainApplication;
import com.exercise.app30day.config.AppConfig;

import java.util.Locale;

public class SpeechUtils {

    private static TextToSpeech textToSpeech;

    private static boolean isInitialized = false;

    public static void init(){
        Context context = MainApplication.INSTANCE.getApplicationContext();
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS){
                int result = textToSpeech.setLanguage(Locale.getDefault());
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, "Language voice not supported", Toast.LENGTH_SHORT).show();
                }else{
                    isInitialized = true;
                }
            }
        });
    }

    public static void speak(String text){
        if(!AppConfig.isVoiceEnabled()) return;
        Context context = MainApplication.INSTANCE.getApplicationContext();
        if(!isInitialized){
            Toast.makeText(context, "TextToSpeech not initialized", Toast.LENGTH_SHORT).show();
        }
        if(text.isEmpty()){
            Toast.makeText(context, "Text is empty", Toast.LENGTH_SHORT).show();
        } else {
            float pitch = AppConfig.getVoiceSpeed();
            float speed = AppConfig.getVoicePitch();
            textToSpeech.setPitch(pitch);
            textToSpeech.setSpeechRate(speed);
            int result = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            if (result == TextToSpeech.ERROR) {
                Toast.makeText(context, "Cannot speak", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static void stop(){
        if(textToSpeech != null){
            textToSpeech.stop();
        }
    }

    public static void destroy(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}
