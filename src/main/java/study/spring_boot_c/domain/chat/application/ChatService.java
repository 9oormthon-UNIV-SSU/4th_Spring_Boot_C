package study.spring_boot_c.domain.chat.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;

public interface ChatService {
    void sendMessage(ChatMessageDTO.MessageReceive dto);

    Page<ChatMessageDTO.RoomMessage> getMessagesByRoomId(Long roomId, Pageable pageable);
}
