package com.exercise.app30day.features.chatbot;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityChatbotBinding;
import com.exercise.app30day.features.chatbot.recyclerview.Message;
import com.exercise.app30day.features.chatbot.recyclerview.MessageAdapter;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatbotActivity extends BaseActivity<ActivityChatbotBinding, ChatbotViewModel>
        implements View.OnClickListener {

    private MessageAdapter messageAdapter;

    @Override
    protected void initView() {
        messageAdapter = new MessageAdapter();
        binding.rvMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessages.setAdapter(messageAdapter);
        
        // Quan sát trạng thái loading
        viewModel.isLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        // Quan sát lỗi
        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        
        // Tải lịch sử chat
        viewModel.loadChatHistory().observe(this, messages -> {
            if (messages != null && !messages.isEmpty()) {
                for (Message message : messages) {
                    messageAdapter.addMessage(message);
                }
                // Cuộn xuống tin nhắn mới nhất
                binding.rvMessages.post(() -> {
                    binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                });
            }
        });
    }

    @Override
    protected void initListener() {
        binding.btnSend.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnSend) {
            sendMessage();
        } else if (v == binding.btnBack) {
            finish();
        }
    }

    private void sendMessage() {
        String messageText = binding.etMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(messageText)) {
            // Thêm tin nhắn của người dùng
            Message userMessage = new Message(messageText, Message.TYPE_USER);
            messageAdapter.addMessage(userMessage);

            // Cuộn xuống tin nhắn mới nhất
            binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

            // Xóa nội dung tin nhắn đã nhập
            binding.etMessage.setText("");
            
            // Gửi tin nhắn và quan sát phản hồi
            viewModel.sendMessage(messageText).observe(this, messages -> {
                if (messages != null && !messages.isEmpty()) {
                    // Hiển thị các tin nhắn từ bot với hiệu ứng delay
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        for (int i = 0; i < messages.size(); i++) {
                            final int index = i;
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                Message botMessage = messages.get(index);
                                messageAdapter.addMessage(botMessage);
                                
                                // Cuộn xuống tin nhắn mới nhất
                                binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                            }, 300 * index);
                        }
                    }, 500);
                }
            });
        }
    }
}