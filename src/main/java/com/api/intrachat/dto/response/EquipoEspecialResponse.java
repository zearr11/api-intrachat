package com.api.intrachat.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipoEspecialResponse {

    private Long id;
    private String sede;
    private String campania;
    private String jefeOperacion;
    private String supervisor;
    private String nombreEquipo;
    private Integer integrantesOperativos;
    private Integer integrantesInoperativos;
    private Double promedioMensajesDiarios;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;

}
