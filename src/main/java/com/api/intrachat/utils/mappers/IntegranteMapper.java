package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.IntegranteResponse;
import com.api.intrachat.models.chat.Integrante;

public class IntegranteMapper {

    public static IntegranteResponse integranteResponse(Integrante integrante) {
        return new IntegranteResponse(
                integrante.getEstado(),
                integrante.getPermiso(),
                UsuarioMapper.usuarioResponse(integrante.getUsuario())
        );
    }

}
