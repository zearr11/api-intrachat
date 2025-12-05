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
public class CampaniaEspecialResponse {

    private Long id;
    private String nombreComercialEmpresa;
    private AreaAtencion areaAtencion;
    private MedioComunicacion medioComunicacion;

    private Integer totalOperacionesActivas;
    private Integer totalOperacionesInactivas;

    private Integer totalEquiposActivos;
    private Integer totalEquiposInactivos;

    private Integer totalUsuariosActivos;
    private Integer totalUsuariosInactivos;

    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;

}
