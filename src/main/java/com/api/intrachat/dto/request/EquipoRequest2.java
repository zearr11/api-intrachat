package com.api.intrachat.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EquipoRequest2 {

    // private Long idEquipo;
    private Long idOperacion;
    private Long idSupervisor;
    private String nombre;
    private String descripcion;
    private List<IntegranteRequest> integrantes;

}
