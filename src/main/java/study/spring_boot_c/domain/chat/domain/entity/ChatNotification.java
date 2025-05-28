package study.spring_boot_c.domain.chat.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import study.spring_boot_c.domain.model.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatNotification extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private String previewMessage;
    private boolean isRead;
    private LocalDateTime notifiedAt;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}

