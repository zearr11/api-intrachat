package com.api.intrachat.utils.constructs;

import com.api.intrachat.utils.dto.response.GeneralResponse;
import com.api.intrachat.utils.constants.ValuesStatus;

public class ResponseConstruct {

    public static GeneralResponse<?> success(Object entity) {
        return GeneralResponse.builder()
                .status(ValuesStatus.STATUS_SUCCESS)
                .data(entity)
                .build();
    }

    public static GeneralResponse<?> errorGeneric(String message) {
        return GeneralResponse.builder()
                .status(ValuesStatus.STATUS_ERROR)
                .message(message)
                .build();
    }

}
