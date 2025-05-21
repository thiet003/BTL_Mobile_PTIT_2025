package com.exercise.app30day.data.repositories.impl;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.dao.ConversationDao;
import com.exercise.app30day.data.dao.HistoryChatDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.models.Conversation;
import com.exercise.app30day.data.models.HistoryChat;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.repositories.ChatbotRepository;
import com.exercise.app30day.features.chatbot.apiservice.ChatbotApiService;
import com.exercise.app30day.features.chatbot.apiservice.RetrofitClient;
import com.exercise.app30day.features.chatbot.models.ChatMessage;
import com.exercise.app30day.features.chatbot.models.ChatbotRequest;
import com.exercise.app30day.features.chatbot.models.ChatbotResponse;
import com.exercise.app30day.features.chatbot.recyclerview.Message;
import com.exercise.app30day.items.UserItem;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ChatbotRepositoryImpl implements ChatbotRepository {

    private final ConversationDao conversationDao;
    private final HistoryChatDao historyChatDao;
    private final UserDao userDao;
    private final ChatbotApiService apiService;
    private final Executor executor;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private String conversationId;

    @Inject
    public ChatbotRepositoryImpl(ConversationDao conversationDao, HistoryChatDao historyChatDao, UserDao userDao) {
        this.conversationDao = conversationDao;
        this.historyChatDao = historyChatDao;
        this.userDao = userDao;
        this.apiService = RetrofitClient.getChatbotService();
        this.executor = Executors.newSingleThreadExecutor();
        
        // Lấy conversation ID từ Hawk hoặc tạo mới
        conversationId = Hawk.get(HawkKeys.CONVERSATION_ID_KEY, null);
        if (conversationId == null) {
            conversationId = UUID.randomUUID().toString();
            Hawk.put(HawkKeys.CONVERSATION_ID_KEY, conversationId);
        }
    }

    @Override
    public LiveData<List<Message>> sendMessage(String userMessage) {
        MutableLiveData<List<Message>> result = new MutableLiveData<>();
        
        executor.execute(() -> {
            try {
                // Lấy thông tin người dùng
                UserItem existingUser = userDao.getUserItemSync();
                if (existingUser == null) {
                    postError("User not found");
                    result.postValue(new ArrayList<>());
                    return;
                }
                
                // Lấy hoặc tạo conversation
                long userId = existingUser.getId();
                long dbConversationId = getOrCreateConversation(userId);
                
                // Lưu tin nhắn người dùng vào database
                saveHistoryChat(dbConversationId, "user", userMessage);
                
                // Lấy tất cả tin nhắn trong cuộc hội thoại
                List<HistoryChat> historyChats = historyChatDao.getHistoryChatsByConversationId(dbConversationId);
                
                // Chuyển đổi thành danh sách ChatMessage để gửi lên API
                List<ChatMessage> chatMessages = new ArrayList<>();
                for (HistoryChat historyChat : historyChats) {
                    chatMessages.add(new ChatMessage(historyChat.getRole(), historyChat.getMessage()));
                }
                
                // Tạo request
                ChatbotRequest request = new ChatbotRequest(chatMessages, conversationId);
                
                // Gọi API trên main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    apiService.getChatbotResponse(request).enqueue(new Callback<ChatbotResponse>() {
                        @Override
                        public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                executor.execute(() -> {
                                    try {
                                        List<Message> botMessages = new ArrayList<>();
                                        
                                        // Lưu các tin nhắn phản hồi vào database
                                        for (ChatbotResponse.Answer answer : response.body().getAnswers()) {
                                            saveHistoryChat(dbConversationId, "agent", answer.getMessage());
                                            botMessages.add(new Message(answer.getMessage(), Message.TYPE_BOT));
                                        }
                                        
                                        result.postValue(botMessages);
                                    } catch (Exception e) {
                                        postError("Error processing response: " + e.getMessage());
                                        result.postValue(new ArrayList<>());
                                    }
                                });
                            } else {
                                postError("Error: " + response.code());
                                result.postValue(new ArrayList<>());
                            }
                        }

                        @Override
                        public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                            postError("Network error: " + t.getMessage());
                            result.postValue(new ArrayList<>());
                        }
                    });
                });
            } catch (Exception e) {
                postError("Error: " + e.getMessage());
                result.postValue(new ArrayList<>());
            }
        });
        
        return result;
    }

    @Override
    public LiveData<List<Message>> loadChatHistory() {
        MutableLiveData<List<Message>> result = new MutableLiveData<>();
        
        executor.execute(() -> {
            try {
                UserItem existingUser = userDao.getUserItemSync();
                if (existingUser == null) {
                    postError("User not found");
                    result.postValue(new ArrayList<>());
                    return;
                }
                
                long userId = existingUser.getId();
                List<Conversation> conversations = conversationDao.getConversationsByUserId(userId);
                
                if (conversations == null || conversations.isEmpty()) {
                    // Không có lịch sử chat
                    result.postValue(new ArrayList<>());
                    return;
                }
                
                // Lấy cuộc hội thoại gần nhất
                long conversationId = conversations.get(0).getId();
                List<HistoryChat> historyChats = historyChatDao.getHistoryChatsByConversationId(conversationId);
                
                // Chuyển đổi thành danh sách Message
                List<Message> messages = new ArrayList<>();
                for (HistoryChat historyChat : historyChats) {
                    int messageType = historyChat.getRole().equals("user") ? Message.TYPE_USER : Message.TYPE_BOT;
                    messages.add(new Message(historyChat.getMessage(), messageType));
                }
                
                result.postValue(messages);
            } catch (Exception e) {
                postError("Error loading chat history: " + e.getMessage());
                result.postValue(new ArrayList<>());
            }
        });
        
        return result;
    }

    @Override
    public LiveData<String> getError() {
        return error;
    }
    
    private void postError(String errorMessage) {
        new Handler(Looper.getMainLooper()).post(() -> error.setValue(errorMessage));
    }
    
    private long getOrCreateConversation(long userId) {
        List<Conversation> conversations = conversationDao.getConversationsByUserId(userId);
        if (conversations != null && !conversations.isEmpty()) {
            return conversations.get(0).getId();
        }
        
        Conversation conversation = new Conversation(userId, System.currentTimeMillis());
        return conversationDao.insert(conversation);
    }
    
    private void saveHistoryChat(long conversationId, String role, String message) {
        HistoryChat historyChat = new HistoryChat(conversationId, role, message, System.currentTimeMillis());
        historyChatDao.insert(historyChat);
    }
}
