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
public class GrupoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String urlFoto;
    private SalaResponse sala;
    private LocalDateTime ultimaModificacion;
    private Boolean estado;

}
