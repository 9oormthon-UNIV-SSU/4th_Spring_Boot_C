package study.spring_boot_c.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import study.spring_boot_c.domain.chat.converter.ChatMessageConverter;
import study.spring_boot_c.domain.chat.domain.entity.ChatMessage;
import study.spring_boot_c.domain.chat.domain.repository.ChatMessageRepository;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ObjectMapper objectMapper;

    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            ChatMessageDTO.MessageReceive chatMessage = objectMapper.readValue(body, ChatMessageDTO.MessageReceive.class);

            // 1. WebSocket으로 클라이언트에게 전송
            messagingTemplate.convertAndSend("/topic/chatroom/" + chatMessage.getRoomId(), chatMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

