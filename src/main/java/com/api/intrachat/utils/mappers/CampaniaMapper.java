package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.CampaniaResponse;
import com.api.intrachat.models.campania.Campania;

public class CampaniaMapper {

    public static CampaniaResponse campaniaResponse(Campania campania) {
        return new CampaniaResponse(
                campania.getId(),
                campania.getNombre(),
                EmpresaMapper.empresaResponse(campania.getEmpresa()),
                campania.getAreaAtencion(),
                campania.getMedioComunicacion(),
                campania.getEstado(),
                campania.getFechaCreacion(),
                campania.getUltimaModificacion()
        );
    }

}
