package study.spring_boot_c.domain.chat.domain.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatNotification {
    @Id @GeneratedValue
    private Long id;

    private Long memberId;
    private Long chatRoomId;
    private String previewMessage;

    private boolean isRead;
    private LocalDateTime notifiedAt;
}

