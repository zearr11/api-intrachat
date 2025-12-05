package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.CampaniaEspecialResponse;
import com.api.intrachat.repositories.campania.projections.CampaniaProjection;
import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;

public class CampaniaMapper {

    public static CampaniaEspecialResponse campaniaResponse(CampaniaProjection campania) {
        return new CampaniaEspecialResponse(
                campania.getId(),
                campania.getNombreComercialEmpresa(),
                AreaAtencion.valueOf(campania.getAreaAtencion()),
                MedioComunicacion.valueOf(campania.getMedioComunicacion()),
                campania.getTotalOperacionesActivas(),
                campania.getTotalOperacionesInactivas(),
                campania.getTotalEquiposActivos(),
                campania.getTotalEquiposInactivos(),
                campania.getTotalUsuariosActivos(),
                campania.getTotalUsuariosInactivos(),
                campania.getEstado(),
                campania.getFechaCreacion(),
                campania.getUltimaModificacion()
        );
    }

}
