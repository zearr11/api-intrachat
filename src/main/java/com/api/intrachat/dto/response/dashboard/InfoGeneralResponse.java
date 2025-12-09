package com.api.intrachat.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoGeneralResponse {

    private Integer usuariosActivos;
    private Integer usuariosInactivos;

    private Integer empresasActivas;
    private Integer empresasInactivas;

    private Integer sedesActivas;
    private Integer sedesInactivas;

    private Integer campaniasActivas;
    private Integer campaniasInactivas;

    private Integer operacionesActivas;
    private Integer operacionesInactivas;

    private Integer equiposActivos;
    private Integer equiposInactivos;

}
