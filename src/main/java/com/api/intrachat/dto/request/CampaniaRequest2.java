package com.api.intrachat.dto.request;

import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CampaniaRequest2 {

    private String nombre;

    private Long idEmpresa;

    private AreaAtencion areaAtencion;
    private MedioComunicacion medioComunicacion;
    private Boolean estado;

}
