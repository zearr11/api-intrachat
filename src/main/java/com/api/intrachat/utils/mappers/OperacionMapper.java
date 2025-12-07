package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.OperacionEspecialResponse;
import com.api.intrachat.dto.response.OperacionResponse;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.repositories.campania.projections.OperacionProjection;

public class OperacionMapper {

    public static OperacionResponse operacionResponse(Operacion operacion) {
        return new OperacionResponse(
                operacion.getId(),
                SedeMapper.sedeResponse(operacion.getSede()),
                CampaniaMapper.campaniaResponse(operacion.getCampania()),
                UsuarioMapper.usuarioResponse(operacion.getJefeOperacion()),
                operacion.getFechaCreacion(),
                operacion.getFechaFinalizacion()
        );
    }

    public static OperacionEspecialResponse operacionEspecialResponse(OperacionProjection operacion) {
        return new OperacionEspecialResponse(
                operacion.getId(),
                operacion.getEmpresa(),
                operacion.getCampania(),
                operacion.getSede(),
                operacion.getJefeOperacion(),
                operacion.getTotalEquiposOperativos(),
                operacion.getTotalEquiposInoperativos(),
                operacion.getTotalUsuariosOperativos(),
                operacion.getTotalUsuariosInoperativos(),
                operacion.getFechaCreacion(),
                operacion.getFechaFinalizacion()
        );

    }

}
