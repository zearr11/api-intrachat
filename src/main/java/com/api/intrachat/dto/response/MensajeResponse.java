package com.api.intrachat.dto.response;

import com.api.intrachat.dto.response.customized.ArchivoResponse;
import com.api.intrachat.dto.response.customized.contact.DatosUsuarioResponse;
import com.api.intrachat.utils.enums.TipoMensaje;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensajeResponse {

    private Long idMensaje;
    private DatosUsuarioResponse remitente;
    private TipoMensaje tipoMensaje;
    private LocalDateTime fechaEnvio;
    private ArchivoResponse archivoResponse; // Opcional: Solo cuando es imagen | archivo
    private String contenido; // Opcional: Solo cuando es texto

}
