package com.exercise.app30day.features.chatbot.apiservice;

import com.exercise.app30day.features.chatbot.models.ChatbotRequest;
import com.exercise.app30day.features.chatbot.models.ChatbotResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotApiService {
    @POST("api/v1/chat") // Replace with your actual API endpoint
    Call<ChatbotResponse> getChatbotResponse(@Body ChatbotRequest request);
}
