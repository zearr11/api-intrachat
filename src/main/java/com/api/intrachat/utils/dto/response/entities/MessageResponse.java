package com.api.intrachat.utils.dto.response.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {

    private Long idChat;
    private String sender;
    private LocalDateTime dateSend;
    // private MessageType messageType;

    private MessageTextResponse messageText;
    private MessageFileResponse messageFile;

}
