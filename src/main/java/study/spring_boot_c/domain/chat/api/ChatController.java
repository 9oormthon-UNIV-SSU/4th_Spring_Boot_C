package study.spring_boot_c.domain.chat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.spring_boot_c.domain.chat.application.ChatService;
import study.spring_boot_c.domain.chat.dto.ChatMessageDTO;
import study.spring_boot_c.global.common.response.BaseResponse;
import study.spring_boot_c.global.error.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Validated
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    @Operation(summary = "채팅 메시지 전송 API", description = "클라이언트가 채팅 메시지를 전송할 때 사용하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CHAT_200", description = "메시지 전송 성공")
    })
    @Parameters({
            @Parameter(name = "roomId", description = "채팅방 ID"),
            @Parameter(name = "senderId", description = "메시지를 보낸 사용자 ID"),
            @Parameter(name = "message", description = "보낼 메시지 내용")
    })
    public BaseResponse<Void> sendChatMessage(@Valid @RequestBody ChatMessageDTO.MessageReceive request) {

        chatService.sendMessage(request);

        return BaseResponse.onSuccess(SuccessStatus.CHAT_SEND_SUCCESS, null);
    }
}

