package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.EquipoEspecialResponse;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.repositories.campania.projections.EquipoProjection;

public class EquipoMapper {

    public static EquipoEspecialResponse equipoResponse(EquipoProjection equipo) {
        return new EquipoEspecialResponse(
                equipo.getIdEquipo(),
                equipo.getSede(),
                equipo.getCampania(),
                equipo.getJefeOperacion(),
                equipo.getSupervisor(),
                equipo.getNombreEquipo(),
                equipo.getIntegrantesActivos(),
                equipo.getIntegrantesInactivos(),
                equipo.getPromedioMensajesDiarios(),
                equipo.getEstado()
        );
    }

}
