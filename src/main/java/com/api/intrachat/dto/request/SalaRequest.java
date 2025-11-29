package com.api.intrachat.dto.request;

import com.api.intrachat.utils.enums.TipoSala;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SalaRequest {

    private TipoSala tipoSala;
    private List<IntegranteRequest> integrantes;

}
