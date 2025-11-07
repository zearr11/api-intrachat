package com.api.intrachat.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SedeRequest {

    private String nombre;
    private String direccion;
    private String ciudad;
    private Long idPais;

}
