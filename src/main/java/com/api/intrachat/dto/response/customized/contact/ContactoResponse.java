package com.api.intrachat.dto.response.customized.contact;

import com.api.intrachat.utils.enums.TipoSala;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactoResponse {

    private Long idSala; // Opcional: Solo obligatorio si hay contacto previo
    private TipoSala tipoSala; // Obligatorio
    private DatosMensajeResponse datosMensaje; // Opcional: Existe si hay un contacto previo
    private DatosGrupoResponse datosGrupo; // Opcional: En caso el mensaje sea de un chat grupal
    private DatosUsuarioResponse datosUsuario; // Opcional: En caso el mensaje sea de un chat privado
    private Boolean existeContactoPrevio; // Obligatorio

}
