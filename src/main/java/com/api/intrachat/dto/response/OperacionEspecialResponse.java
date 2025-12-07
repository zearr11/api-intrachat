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
public class OperacionEspecialResponse {

    private Long id;
    private String empresa;
    private String campania;
    private String sede;
    private String jefeOperacion;
    private Integer totalEquiposOperativos;
    private Integer totalEquiposInoperativos;
    private Integer totalUsuariosOperativos;
    private Integer totalUsuariosInoperativos;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion; // Cuando esta presente, la operacion esta marcada como finalizada

}
