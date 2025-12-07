package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.*;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.repositories.campania.projections.EquipoProjection;
import java.util.List;

public class EquipoMapper {

    public static EquipoResponse equipoResponse(Equipo equipo,
                                                List<Integrante> integrantes,
                                                List<EquipoUsuarios> equipoUsuarios) {

        OperacionResponse datosOperacion = OperacionMapper.operacionResponse(equipo.getOperacion());
        UsuarioResponse datosSupervisor = UsuarioMapper.usuarioResponse(equipo.getSupervisor());
        GrupoResponse datosGrupo = GrupoMapper.grupoResponse(equipo.getGrupo(), integrantes);
        List<IntegranteCompletoResponse> integrantesCompletos = equipoUsuarios.stream()
                .map(IntegranteMapper::integranteCompletoResponse)
                .toList();

        return new EquipoResponse(
                equipo.getId(),
                datosOperacion,
                datosSupervisor,
                datosGrupo,
                equipo.getFechaCreacion(),
                equipo.getFechaCierre(),
                integrantesCompletos
        );
    }

    public static EquipoEspecialResponse equipoEspecialResponse(EquipoProjection equipo) {
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
                equipo.getFechaCreacion(),
                equipo.getFechaCierre()
        );
    }

}
