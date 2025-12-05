package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.OperacionEspecialResponse;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.repositories.campania.projections.OperacionProjection;

public class OperacionMapper {

    public static OperacionEspecialResponse operacionResponse(OperacionProjection operacion) {
        return new OperacionEspecialResponse(
                operacion.getId(),
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
