package com.api.intrachat.dto.request;

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
    private Long idArea;

    private MedioComunicacion medioComunicacion;
    private Boolean estado;

}
