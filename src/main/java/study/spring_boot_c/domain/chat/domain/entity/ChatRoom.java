package study.spring_boot_c.domain.chat.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import study.spring_boot_c.domain.model.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatNotification> notifications;
}

