package com.exercise.app30day.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.Toast;

import com.exercise.app30day.MainApplication;
import com.exercise.app30day.config.AppConfig;

import java.util.Locale;
import java.util.Set;

public class SpeechHelper implements TextToSpeech.OnInitListener {

    private static volatile SpeechHelper instance;

    private TextToSpeech textToSpeech;

    private boolean isInitialized = false;

    public static SpeechHelper getInstance() {
        if (instance == null) {
            synchronized (SpeechHelper.class) {
                if (instance == null) {
                    instance = new SpeechHelper();
                }
            }
        }
        return instance;
    }

    public void init(){
        Context context = MainApplication.INSTANCE.getApplicationContext();
        textToSpeech = new TextToSpeech(context, this);
    }

    public void speak(String text){
        if(!AppConfig.isVoiceEnabled()) return;
        Context context = MainApplication.INSTANCE.getApplicationContext();
        if(!isInitialized){
            Toast.makeText(context, "TextToSpeech not initialized", Toast.LENGTH_SHORT).show();
        }
        if(text.isEmpty()){
            Toast.makeText(context, "Text is empty", Toast.LENGTH_SHORT).show();
        } else {
            float pitch = 1.0f;
            float speed = 1.0f;
            textToSpeech.setPitch(pitch);
            textToSpeech.setSpeechRate(speed);
            int result = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            if (result == TextToSpeech.ERROR) {
                Toast.makeText(context, "Cannot speak", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void stop(){
        if(textToSpeech != null){
            textToSpeech.stop();
        }
    }

    public void destroy(){
        if(textToSpeech != null){
            textToSpeech.shutdown();
            instance = null;
            textToSpeech = null;
        }
    }

    @Override
    public void onInit(int status) {
        Context context = MainApplication.INSTANCE.getApplicationContext();
        try {
            if (status == TextToSpeech.SUCCESS){
                int result = textToSpeech.setLanguage(Locale.getDefault());
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
                    textToSpeech.setLanguage(Locale.US);
                }
                isInitialized = true;
            }
        } catch (Exception e) {
            throw new IllegalStateException("You must call init method");
        }
    }
}
