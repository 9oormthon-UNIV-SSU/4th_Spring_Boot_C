package study.spring_boot_c.domain.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.spring_boot_c.domain.chat.converter.ChatMessageConverter;
import study.spring_boot_c.domain.chat.domain.entity.ChatMessage;
import study.spring_boot_c.domain.chat.domain.repository.ChatMessageRepository;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;
import study.spring_boot_c.domain.chat.exception.ChatException;
import study.spring_boot_c.global.error.code.status.ErrorStatus;
import study.spring_boot_c.global.redis.RedisPublisher;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final RedisPublisher redisPublisher;
    private final ChatMessageRepository chatMessageRepository; // MongoDB 저장용
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public void sendMessage(ChatMessageDTO.MessageReceive dto) {
        // 멤버 체크 + 방 체크 구현 로직 필요
        ChatMessage message = ChatMessageConverter.toChatMessage(dto);
        String channel = "chatroom:" + dto.getRoomId();

        try {
            chatMessageRepository.save(message);
        } catch (Exception e) {
            throw new ChatException(ErrorStatus.DB_ERROR);
        }

        try {
            String json = objectMapper.writeValueAsString(message);
            redisPublisher.publish(channel, json);
        } catch (Exception e) {
            throw new ChatException(ErrorStatus.REDIS_ERROR);
        }

    }

    @Override
    public Page<ChatMessageDTO.RoomMessage> getMessagesByRoomId(Long roomId, Pageable pageable) {
        return chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId, pageable)
                .map(ChatMessageConverter::toRoomMessages);
    }
}
