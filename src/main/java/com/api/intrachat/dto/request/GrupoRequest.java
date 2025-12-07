package com.api.intrachat.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GrupoRequest {

    private String descripcion;
    private String nombre;
    private Long idSala;

}
