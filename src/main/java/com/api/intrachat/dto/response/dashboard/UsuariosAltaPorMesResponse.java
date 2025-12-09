package com.api.intrachat.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuariosAltaPorMesResponse {

    private String nombreMes;
    private Long cantidad;

}
