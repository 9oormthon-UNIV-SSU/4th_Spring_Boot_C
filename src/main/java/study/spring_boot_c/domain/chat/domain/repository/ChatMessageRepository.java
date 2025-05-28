package study.spring_boot_c.domain.chat.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import study.spring_boot_c.domain.chat.domain.entity.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
