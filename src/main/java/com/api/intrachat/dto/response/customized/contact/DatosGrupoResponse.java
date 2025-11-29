package com.api.intrachat.dto.response.customized.contact;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DatosGrupoResponse {

    private Long idGrupo;
    private String urlFoto;
    private String nombreGrupo;
    private String descripcion;

}
