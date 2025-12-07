package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.CampaniaEspecialResponse;
import com.api.intrachat.dto.response.CampaniaResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.repositories.campania.projections.CampaniaProjection;
import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;

public class CampaniaMapper {

    public static CampaniaResponse campaniaResponse(Campania campania) {
        return new CampaniaResponse(
                campania.getId(),
                campania.getNombre(),
                campania.getAreaAtencion(),
                campania.getMedioComunicacion(),
                campania.getEstado(),
                campania.getFechaCreacion(),
                campania.getUltimaModificacion(),
                EmpresaMapper.empresaResponse(campania.getEmpresa())
        );
    }

    public static CampaniaEspecialResponse campaniaEspecialResponse(CampaniaProjection campania) {
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
