package com.api.intrachat.services.interfaces.chat;

import com.api.intrachat.dto.request.GrupoRequest;
import com.api.intrachat.models.chat.Grupo;

public interface IGrupoService {

    Grupo obtenerGrupoPorId(Long idGrupo);
    Grupo obtenerGrupoPorSala(Long idSala);

    Grupo crearGrupo(GrupoRequest grupoRequest);
}
