package com.api.intrachat.services.impl.chat;

import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.repositories.chat.GrupoRepository;
import com.api.intrachat.services.interfaces.chat.IGrupoService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import org.springframework.stereotype.Service;

@Service
public class GrupoService implements IGrupoService {

    private final GrupoRepository grupoRepository;
    private final ISalaService salaService;

    public GrupoService(GrupoRepository grupoRepository,
                        ISalaService salaService) {
        this.grupoRepository = grupoRepository;
        this.salaService = salaService;
    }

    @Override
    public Grupo obtenerGrupoPorId(Long idGrupo) {
        return grupoRepository.findById(idGrupo).orElseThrow(
                () -> new ErrorException404(GeneralConstants.mensajeEntidadNoExiste(
                        "Grupo", idGrupo.toString()))
        );
    }

    @Override
    public Grupo obtenerGrupoPorSala(Long idSala) {
        Sala sala = salaService.obtenerSalaPorId(idSala);
        return grupoRepository.findBySala(sala).orElse(null);
    }

}
