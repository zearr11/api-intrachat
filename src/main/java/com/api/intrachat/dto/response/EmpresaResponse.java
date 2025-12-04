package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaResponse {

    private Long id;
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String correo;
    private String telefono;
    private Boolean estado;

}
