package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.Permiso;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IntegranteResponse {

    private Boolean estado;
    private Permiso permiso;
    private UsuarioResponse usuarioResponse; // temp

}
