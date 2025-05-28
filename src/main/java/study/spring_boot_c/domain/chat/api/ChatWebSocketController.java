package study.spring_boot_c.domain.chat.api;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import study.spring_boot_c.domain.chat.application.ChatService;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat/send")
    public void handleWebSocketMessage(ChatMessageDTO.MessageReceive message) {
        chatService.sendMessage(message);
    }
}

