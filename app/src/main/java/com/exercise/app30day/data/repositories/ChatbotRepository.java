package com.exercise.app30day.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.exercise.app30day.features.chatbot.recyclerview.Message;

import java.util.List;

public interface ChatbotRepository {
    /**
     * Gửi tin nhắn đến chatbot API và lưu vào database
     * @param userMessage Nội dung tin nhắn người dùng
     * @return LiveData chứa danh sách tin nhắn phản hồi từ chatbot
     */
    LiveData<List<Message>> sendMessage(String userMessage);
    
    /**
     * Tải lịch sử chat từ database
     * @return LiveData chứa danh sách tin nhắn lịch sử
     */
    LiveData<List<Message>> loadChatHistory();
    
    /**
     * Xử lý lỗi trong quá trình gửi tin nhắn hoặc tải lịch sử
     * @return LiveData chứa thông báo lỗi
     */
    LiveData<String> getError();
}
