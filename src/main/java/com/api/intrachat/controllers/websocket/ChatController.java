package com.api.intrachat.controllers.websocket;

import com.api.intrachat.dto.request.customized.ChatRequest;
import com.api.intrachat.services.impl.chat.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/send")
    public void sendMessage(ChatRequest chatRequest, Principal principal) {
        chatService.enviarMensaje(chatRequest, principal);
    }

}
