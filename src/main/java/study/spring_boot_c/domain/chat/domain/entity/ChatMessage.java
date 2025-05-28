package study.spring_boot_c.domain.chat.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import study.spring_boot_c.domain.model.entity.BaseEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@Document(collection = "chat_messages")
public class ChatMessage {
    @Id
    private String id;
    private Long roomId;
    private Long senderId;
    private String message;
    private LocalDateTime timestamp;
}

