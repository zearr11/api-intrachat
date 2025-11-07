package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SedeResponse {

    private Long id;
    private String sede;
    private String direccion;
    private String ciudad;
    private PaisResponse pais;

}
