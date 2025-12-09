package com.api.intrachat.services.impl.chat;

import com.api.intrachat.dto.request.GrupoRequest;
import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.models.general.Archivo;
import com.api.intrachat.repositories.chat.GrupoRepository;
import com.api.intrachat.services.interfaces.chat.IGrupoService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IArchivoService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.GrupoConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class GrupoService implements IGrupoService {

    private final GrupoRepository grupoRepository;
    private final ISalaService salaService;
    private final IArchivoService archivoService;

    public GrupoService(GrupoRepository grupoRepository,
                        ISalaService salaService,
                        IArchivoService archivoService) {
        this.grupoRepository = grupoRepository;
        this.salaService = salaService;
        this.archivoService = archivoService;
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

    @Override
    public Grupo crearGrupo(GrupoRequest grupoRequest) {
        Archivo archivoDefault = archivoService.obtenerArchivoPorID(GrupoConstants.ID_ARCHIVO_DEFAULT);
        Sala salaGrupo = salaService.obtenerSalaPorId(grupoRequest.getIdSala());

        Grupo nuevoGrupo = new Grupo(
                null,
                grupoRequest.getNombre(),
                grupoRequest.getDescripcion(),
                archivoDefault,
                salaGrupo,
                LocalDateTime.now(),
                GeneralConstants.ESTADO_DEFAULT);

        nuevoGrupo = grupoRepository.save(nuevoGrupo);

        return nuevoGrupo;
    }

}
