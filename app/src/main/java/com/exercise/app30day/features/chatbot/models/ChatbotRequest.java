package com.exercise.app30day.features.chatbot.models;

import java.util.List;

public class ChatbotRequest {
    private List<ChatMessage> messages;
    private String conversation_id;

    public ChatbotRequest(List<ChatMessage> messages, String conversation_id) {
        this.messages = messages;
        this.conversation_id = conversation_id;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }
}
