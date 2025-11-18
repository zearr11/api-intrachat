package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.EquipoResponse;
import com.api.intrachat.models.campania.Equipo;

public class EquipoMapper {

    public static EquipoResponse equipoResponse(Equipo equipo) {
        return new EquipoResponse();
    }

}
