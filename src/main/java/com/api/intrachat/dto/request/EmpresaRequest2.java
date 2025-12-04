package com.api.intrachat.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaRequest2 {

    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String correo;
    private String telefono;
    private Boolean estado;

}
