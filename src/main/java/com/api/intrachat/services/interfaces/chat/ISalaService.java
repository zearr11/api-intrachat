package com.api.intrachat.services.interfaces.chat;

import com.api.intrachat.dto.request.SalaRequest;
import com.api.intrachat.dto.request.SalaRequest2;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import java.util.List;

public interface ISalaService {

    Sala obtenerSalaPorId(Long id);
    Sala obtenerSalaPrivadaPorIntegrantes(Long idUsuario1, Long idUsuario2);
    List<Sala> obtenerSalasPorUsuario(Long idUsuario);
    List<Integrante> obtenerIntegrantesDeSala(Long idSala);

    Sala crearSala(SalaRequest salaRequest);
    Sala actualizarIntegrantesDeSalaGrupal(Long id, SalaRequest2 salaRequest);

}
