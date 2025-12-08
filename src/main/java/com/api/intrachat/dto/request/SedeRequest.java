package com.api.intrachat.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SedeRequest {

    private String nombre;
    private String direccion;
    private Integer numeroPostal;

}
