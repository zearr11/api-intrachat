package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.IntegranteCompletoResponse;
import com.api.intrachat.dto.response.IntegranteResponse;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.chat.Integrante;

public class IntegranteMapper {

    public static IntegranteCompletoResponse integranteCompletoResponse(EquipoUsuarios equipoUsuarios) {
        return new IntegranteCompletoResponse(
                equipoUsuarios.getId(),
                equipoUsuarios.getFechaInicio(),
                equipoUsuarios.getFechaFin(),
                equipoUsuarios.getPermiso(),
                UsuarioMapper.usuarioResponse(equipoUsuarios.getUsuario())
        );
    }

    public static IntegranteResponse integranteResponse(Integrante integrante) {
        return new IntegranteResponse(
                integrante.getEstado(),
                integrante.getPermiso(),
                UsuarioMapper.usuarioResponse(integrante.getUsuario())
        );
    }

}
