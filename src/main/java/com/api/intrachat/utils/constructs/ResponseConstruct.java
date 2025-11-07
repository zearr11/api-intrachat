package com.api.intrachat.utils.constructs;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.utils.constants.StatusConstants;

public class ResponseConstruct {

    public static GeneralResponse<?> generarRespuestaExitosa(Object entity) {
        return GeneralResponse.builder()
                .status(StatusConstants.STATUS_OK)
                .data(entity)
                .build();
    }

    public static GeneralResponse<?> generarRespuestaFallida(String message) {
        return GeneralResponse.builder()
                .status(StatusConstants.STATUS_FALLIDO)
                .message(message)
                .build();
    }

    public static GeneralResponse<?> generarRespuestaConError(String message) {
        return GeneralResponse.builder()
                .status(StatusConstants.STATUS_ERROR)
                .message(message)
                .build();
    }

}
