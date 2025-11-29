package com.api.intrachat.dto.response.customized;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArchivoResponse {

    private Long id;
    private String nombre;
    private Double tamanio;
    private String tipo;
    private String url;

}
