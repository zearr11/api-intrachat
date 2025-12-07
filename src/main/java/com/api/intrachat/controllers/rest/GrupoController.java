package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.response.GrupoResponse;
import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.services.interfaces.chat.IGrupoService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.mappers.GrupoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.prefix}/grupos")
public class GrupoController {

    private final IGrupoService grupoService;
    private final ISalaService salaService;

    public GrupoController(IGrupoService grupoService,
                           ISalaService salaService) {
        this.grupoService = grupoService;
        this.salaService = salaService;
    }

    @GetMapping("/salas/{idSala}")
    public ResponseEntity<GeneralResponse<?>> obtenerGrupoPorSala(@PathVariable Long idSala) {
        Grupo grupo = grupoService.obtenerGrupoPorSala(idSala);
        if (grupo == null)
            throw new ErrorException404(GeneralConstants.MENSAJE_GENERICO_NO_EXISTE);
        List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(idSala).stream()
                .filter(Integrante::getEstado)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(GrupoMapper.grupoResponse(grupo, integrantes))
        );
    }
}
