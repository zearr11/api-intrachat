package com.api.intrachat.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EquipoResponse {

    private Long idEquipo;
    private OperacionResponse datosOperacion;
    private UsuarioResponse datosSupervisor;
    private GrupoResponse datosGrupo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;
    private List<IntegranteCompletoResponse> integrantes;

}
