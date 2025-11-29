package com.api.intrachat.dto.response.customized.contact;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DatosMensajeResponse {

    private Boolean ultimoMensajeEsMio;
    private String texto;
    private LocalDateTime horaEnvio;

}
