package study.spring_boot_c.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageReceive {
        private Long roomId;
        private Long senderId;
        private String message;
        private LocalDateTime timestamp;
    }
}
