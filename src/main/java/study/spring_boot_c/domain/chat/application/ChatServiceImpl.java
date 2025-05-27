package study.spring_boot_c.domain.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.spring_boot_c.domain.chat.converter.ChatMessageConverter;
import study.spring_boot_c.domain.chat.domain.repository.ChatMessageRepository;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;
import study.spring_boot_c.global.redis.RedisPublisher;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final RedisPublisher redisPublisher;
    private final ChatMessageRepository chatMessageRepository; // MongoDB 저장용
    private final ObjectMapper objectMapper;

    /**
     * 채팅 메시지를 Redis로 발행하고 MongoDB에 저장한다
     */
    public void sendMessage(ChatMessageDTO.MessageReceive dto) {
        try {
            // 1. MongoDB에 저장
            chatMessageRepository.save(ChatMessageConverter.toChatMessage(dto));

            // 2. Redis 채널에 메시지 발행
            String json = objectMapper.writeValueAsString(dto);
            String channel = "chatroom:" + dto.getRoomId();  // 예: chatroom:1
            redisPublisher.publish(channel, json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("메시지 직렬화 실패", e);
        }
    }
}
