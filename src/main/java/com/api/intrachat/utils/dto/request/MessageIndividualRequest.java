package com.api.intrachat.utils.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageIndividualRequest {

    private String sender;
    private String receiver;
    private String content;
    // private MessageType type;

}
