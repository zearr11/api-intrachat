package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.OperacionResponse;
import com.api.intrachat.models.campania.Operacion;

public class OperacionMapper {

    public static OperacionResponse operacionResponse(Operacion operacion) {
        return null;
        /*
        return new OperacionResponse(
                operacion.getId(),
                CampaniaMapper.campaniaResponse(operacion.getCampania()),
                UsuarioMapper.usuarioResponse(operacion.getJefeOperacion()),
                operacion.getEstado()
        );
        */
    }

}
