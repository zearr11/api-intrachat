package com.api.intrachat.controllers.websocket;

import com.api.intrachat.dto.response.customized.current_chat.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestWebSocket {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/private")
    public void test() {
        simpMessagingTemplate.convertAndSendToUser(
                "3",
                "/queue/messages",
                new ChatResponse(1L,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "Prueba OKKK",
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/group")
    public void test2() {
        ChatResponse prueba = new ChatResponse(1L,
                null,
                null,
                null,
                null,
                null,
                null,
                "Prueba OKKK",
                LocalDateTime.now()
        );
        simpMessagingTemplate.convertAndSend(
                "/topic/group.1",
                prueba
        );
    }

}
