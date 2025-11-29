package com.api.intrachat.dto.response.customized.current_chat;

import com.api.intrachat.dto.response.GrupoResponse;
import com.api.intrachat.dto.response.UsuarioResponse;
import com.api.intrachat.dto.response.customized.ArchivoResponse;
import com.api.intrachat.utils.enums.TipoMensaje;
import com.api.intrachat.utils.enums.TipoSala;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {

    private Long idSala; // Obligatorio
    private UsuarioResponse usuarioRemitente; // Obligatorio
    private UsuarioResponse usuarioDestino; // Solo si es mensaje privado
    private GrupoResponse grupoDestino; // Solo si es mensaje grupal
    private TipoSala tipoSala; // Obligatorio
    private TipoMensaje tipoMensaje; // Obligatorio
    private ArchivoResponse archivoResponse; // Solo si el mensaje es archivo o imagen
    private String texto; // Solo si el mensaje es texto
    private LocalDateTime horaEnvio; // Obligatorio

}
