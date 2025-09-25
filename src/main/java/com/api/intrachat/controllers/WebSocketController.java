package com.api.intrachat.controllers;

import com.api.intrachat.services.interfaces.communication.IMessageService;
import com.api.intrachat.utils.dto.request.MessageIndividualRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IMessageService messageService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate,
                               IMessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    /*
    @MessageMapping("/chat.send") // El usuario envia al backend a traves de esta ruta
    @SendTo("/topic/group") // Y el backend envia a esta ruta el mensaje. Solo los usuarios suscritos a esta ruta reciben el mensaje
    public MessageGroupRequest sendGroupMessage(MessageGroupRequest chatMessage, Principal principal) {
        chatMessage.setSender(principal.getName());
        chatMessage.setType(MessageType.GROUP);
        chatService.saveMessage(chatMessage);
        return chatMessage;
    }
    */

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(MessageIndividualRequest chatMessage, Principal principal) {
        chatMessage.setSender(principal.getName());
        messageService.saveMessageIndividual(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(),
                "/queue/messages",
                chatMessage
        );
    }

}
