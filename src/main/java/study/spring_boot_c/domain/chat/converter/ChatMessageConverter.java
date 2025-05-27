package study.spring_boot_c.domain.chat.converter;

import org.springframework.stereotype.Component;
import study.spring_boot_c.domain.chat.domain.entity.ChatMessage;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;

import java.time.LocalDateTime;

@Component
public class ChatMessageConverter {

    public static ChatMessage toChatMessage(ChatMessageDTO.MessageReceive dto) {
        return ChatMessage.builder()
                .roomId(dto.getRoomId())
                .senderId(dto.getSenderId())
                .message(dto.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
