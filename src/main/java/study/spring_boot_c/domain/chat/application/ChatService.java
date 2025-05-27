package study.spring_boot_c.domain.chat.application;

import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;

public interface ChatService {
    void sendMessage(ChatMessageDTO.MessageReceive dto);
}
