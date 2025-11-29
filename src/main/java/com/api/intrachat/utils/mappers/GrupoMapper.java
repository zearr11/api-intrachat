package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.GrupoResponse;
import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Integrante;
import java.util.List;

public class GrupoMapper {

    public static GrupoResponse grupoResponse(Grupo grupo, List<Integrante> integrantes) {
        return new GrupoResponse(
                grupo.getId(),
                grupo.getNombre(),
                grupo.getDescripcion(),
                grupo.getImagenGrupo().getUrl(),
                SalaMapper.salaResponse(grupo.getSala(), integrantes),
                grupo.getUltimaModificacion(),
                grupo.getEstado()
        );
    }

}
