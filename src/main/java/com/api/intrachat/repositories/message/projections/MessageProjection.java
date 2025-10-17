package com.api.intrachat.repositories.message.projections;

import java.time.LocalDateTime;

public interface MessageProjection {

    Long getIdMessage();
    String getMessageType();
    LocalDateTime getDateSend();
    Long getIdUserSender();
    Long getIdTypeMessage();

}
