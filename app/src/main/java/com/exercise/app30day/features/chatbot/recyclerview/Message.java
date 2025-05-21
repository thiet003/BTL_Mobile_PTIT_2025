package com.exercise.app30day.features.chatbot.recyclerview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    public static final int TYPE_BOT = 0;
    public static final int TYPE_USER = 1;

    private String content;
    private int type;
    private String time;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.time = sdf.format(new Date());
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }
}