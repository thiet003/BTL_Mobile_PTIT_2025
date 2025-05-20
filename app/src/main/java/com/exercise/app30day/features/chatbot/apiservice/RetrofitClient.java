package com.exercise.app30day.features.chatbot.apiservice;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://170.205.36.201:8001/"; // Replace with your actual API base URL
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ChatbotApiService getChatbotService() {
        return getClient().create(ChatbotApiService.class);
    }
}
