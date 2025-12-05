package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    private Boolean estado;

}
