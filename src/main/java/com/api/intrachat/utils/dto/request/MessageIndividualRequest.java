package com.api.intrachat.utils.dto.request;

import com.api.intrachat.utils.enums.MessageType;
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
    private MessageType type;

}
