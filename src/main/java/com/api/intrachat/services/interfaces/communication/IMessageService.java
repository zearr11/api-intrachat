package com.api.intrachat.services.interfaces.communication;

import com.api.intrachat.models.communication.Message;
import com.api.intrachat.utils.dto.request.MessageGroupRequest;
import com.api.intrachat.utils.dto.request.MessageIndividualRequest;
import java.util.List;

public interface IMessageService {

    Message saveMessageIndividual(MessageIndividualRequest request);
    Message saveMessageGroup(MessageGroupRequest request);
    List<Message> getPreviousMessagesChatIndividual();
    List<Message> getPreviousMessagesChatGroup();

}
