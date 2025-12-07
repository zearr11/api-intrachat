package com.api.intrachat.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperacionResponse {

    private Long idOperacion;
    private SedeResponse datosSede;
    private CampaniaResponse datosCampania;
    private UsuarioResponse datosJefeOperacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;

}
