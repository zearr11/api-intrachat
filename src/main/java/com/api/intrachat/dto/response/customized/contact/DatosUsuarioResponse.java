package com.api.intrachat.dto.response.customized.contact;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DatosUsuarioResponse {

    // En caso el mensaje sea un chat privado

    private Long idUsuario;
    private String urlFoto;
    private String nombreYApellido;
    private String descripcion;

}
