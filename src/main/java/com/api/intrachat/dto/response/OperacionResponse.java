package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperacionResponse {

    private Long id;
    private CampaniaEspecialResponse campania;
    private UsuarioResponse jefeOperacion;
    private Boolean estado;

}
