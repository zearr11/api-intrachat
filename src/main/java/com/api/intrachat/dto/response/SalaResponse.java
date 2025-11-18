package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.TipoSala;
import java.time.LocalDateTime;

public class SalaResponse {

    private Long id;
    private TipoSala tipoSala;
    private LocalDateTime fechaCreacion;

}
