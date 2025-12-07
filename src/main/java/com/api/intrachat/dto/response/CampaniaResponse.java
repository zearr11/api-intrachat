package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CampaniaResponse {

    private Long idCampania;
    private String nombre;
    private AreaAtencion areaAtencion;
    private MedioComunicacion medioComunicacion;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private EmpresaResponse datosEmpresa;

}
