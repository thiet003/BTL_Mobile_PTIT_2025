package com.exercise.app30day.features.chatbot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.ChatbotRepository;
import com.exercise.app30day.features.chatbot.recyclerview.Message;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatbotViewModel extends ViewModel {

    private final ChatbotRepository chatbotRepository;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Inject
    public ChatbotViewModel(ChatbotRepository chatbotRepository) {
        this.chatbotRepository = chatbotRepository;
    }

    /**
     * Gửi tin nhắn đến chatbot
     * @param message Nội dung tin nhắn
     * @return LiveData chứa danh sách tin nhắn phản hồi
     */
    public LiveData<List<Message>> sendMessage(String message) {
        isLoading.setValue(true);
        LiveData<List<Message>> result = chatbotRepository.sendMessage(message);
        isLoading.setValue(false);
        return result;
    }

    /**
     * Tải lịch sử chat
     * @return LiveData chứa danh sách tin nhắn lịch sử
     */
    public LiveData<List<Message>> loadChatHistory() {
        isLoading.setValue(true);
        LiveData<List<Message>> result = chatbotRepository.loadChatHistory();
        isLoading.setValue(false);
        return result;
    }

    /**
     * Lấy thông báo lỗi
     * @return LiveData chứa thông báo lỗi
     */
    public LiveData<String> getError() {
        return chatbotRepository.getError();
    }

    /**
     * Kiểm tra trạng thái đang tải
     * @return LiveData chứa trạng thái đang tải
     */
    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}
