package com.api.intrachat.dto.request.customized;

import com.api.intrachat.utils.enums.TipoMensaje;
import com.api.intrachat.utils.enums.TipoSala;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private Long idSala; // Solo cuando es chat grupal o si es chat privado y hay conversación previa
    private Long idUsuarioDestino; // Solo cuando es chat privado
    private TipoSala tipoSala; // OBLIGATORIO
    private TipoMensaje tipoMensaje; // OBLIGATORIO
    // Falta el multipartfile archivo
    private String texto; // Solo cuando el tipo de mensaje es texto

}
