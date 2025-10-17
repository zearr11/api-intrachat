package com.api.intrachat.services.interfaces.message;

import com.api.intrachat.utils.dto.request.MessageGroupRequest;
import com.api.intrachat.utils.dto.request.MessageIndividualRequest;
import com.api.intrachat.utils.dto.response.entities.MessageResponse;
import com.api.intrachat.utils.dto.response.general.PaginatedResponse;

import java.util.List;

public interface IMessageService {

    Long getIdChatIndividual(Long idReceiver);
    void saveMessageIndividual(MessageIndividualRequest request);
    void saveMessageGroup(MessageGroupRequest request);
    PaginatedResponse<List<MessageResponse>> getPreviousMessagesChatIndividual(Long idReceiver, int page, int size);
    PaginatedResponse<List<MessageResponse>> getPreviousMessagesChatGroup(int page, int size);

}
