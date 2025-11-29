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

    private Long idSala;
    private Long idUsuarioDestino;
    private TipoSala tipoSala;
    private TipoMensaje tipoMensaje;
    // Falta el multipartfile archivo
    private String texto;

}
