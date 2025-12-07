package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.Permiso;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IntegranteCompletoResponse {

    private Long idEquipoUsuario;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Permiso permiso;
    private UsuarioResponse datosIntegrante;

}
