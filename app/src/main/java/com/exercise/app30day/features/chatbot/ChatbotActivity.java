package com.exercise.app30day.features.chatbot;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityChatbotBinding;

import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatbotActivity extends BaseActivity<ActivityChatbotBinding, NoneViewModel>
        implements View.OnClickListener {

    private MessageAdapter messageAdapter;
    private Random random = new Random();
    private String[] botResponses = {
            "Xin chào! Tôi là Healthy Chatbot. Tôi có thể giúp gì cho bạn?",
            "Rất tốt! Bạn nên tập thể dục thường xuyên để duy trì sức khỏe.",
            "Uống đủ nước mỗi ngày rất quan trọng cho sức khỏe của bạn.",
            "Bạn nên ăn nhiều rau củ quả và giảm thực phẩm chế biến sẵn.",
            "Ngủ đủ 7-8 tiếng mỗi đêm sẽ giúp cơ thể hồi phục tốt hơn.",
            "Tập yoga có thể giúp giảm stress và cải thiện sức khỏe tinh thần.",
            "Chạy bộ là cách tốt để tăng cường sức khỏe tim mạch.",
            "Thực đơn cân bằng giúp cung cấp đầy đủ dưỡng chất cho cơ thể.",
            "Vitamin D rất quan trọng, bạn nên phơi nắng vào buổi sáng.",
            "Hãy cố gắng hạn chế đồ ngọt và thức ăn nhiều dầu mỡ."
    };

    @Override
    protected void initView() {
        messageAdapter = new MessageAdapter();
        binding.rvMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessages.setAdapter(messageAdapter);
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

            // Bot gửi phản hồi sau 500ms
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Random số lượng tin nhắn bot gửi (1-3)
                int numResponses = random.nextInt(3) + 1;
                for (int i = 0; i < numResponses; i++) {
                    // Trễ giữa các tin nhắn của bot
                    final int index = i;
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        // Random nội dung tin nhắn từ mảng botResponses
                        String botReply = botResponses[random.nextInt(botResponses.length)];
                        Message botMessage = new Message(botReply, Message.TYPE_BOT);
                        messageAdapter.addMessage(botMessage);

                        // Cuộn xuống tin nhắn mới nhất
                        binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                    }, 300 * index);
                }
            }, 500);
        }
    }
}