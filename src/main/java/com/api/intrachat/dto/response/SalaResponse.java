package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.TipoSala;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SalaResponse {

    private Long id;
    private TipoSala tipoSala;
    private LocalDateTime fechaCreacion;
    private List<IntegranteResponse> integrantes;

}
