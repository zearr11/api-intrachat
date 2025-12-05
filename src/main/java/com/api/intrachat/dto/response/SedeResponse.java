package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SedeResponse {

    private Long id;
    private String nombre;
    private String direccion;
    private Integer codigoPostal;
    private Boolean estado;

}
