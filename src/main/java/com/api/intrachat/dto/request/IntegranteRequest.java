package com.api.intrachat.dto.request;

import com.api.intrachat.utils.enums.Permiso;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IntegranteRequest {

    private Permiso tipoPermiso;
    private Long idUsuario;

}
