package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.response.SalaResponse;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.enums.TipoSala;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.mappers.SalaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("${app.prefix}/salas")
public class SalaController {

    private final ISalaService salaService;
    private final IUsuarioService usuarioService;

    public SalaController(ISalaService salaService,
                          IUsuarioService usuarioService) {
        this.salaService = salaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<GeneralResponse<?>> obtenerSalaPorId(@PathVariable Long idSala) {
        Sala sala = salaService.obtenerSalaPorId(idSala);
        List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(sala.getId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                SalaMapper.salaResponse(sala, integrantes)
        ));
    }

    @GetMapping("/usuario-actual")
    public ResponseEntity<GeneralResponse<?>> obtenerSalasDeUsuarioActual() {
        List<Sala> salas = salaService.obtenerSalasPorUsuario(
                usuarioService.obtenerUsuarioActual().getId()
        );
        List<SalaResponse> salasDeUsuario = salas.stream()
                .filter(sala -> sala.getTipoSala() == TipoSala.GRUPO)
                .map(sala -> {
                    List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(sala.getId());
                    return SalaMapper.salaResponse(sala, integrantes);
                })
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(salasDeUsuario)
        );
    }

    @GetMapping("/integrantes/{idUsuarioConsulta}")
    public ResponseEntity<GeneralResponse<?>> obtenerSalaPrivadaPorIntegrantes(@PathVariable Long idUsuarioConsulta) {
        Sala sala = salaService.obtenerSalaPrivadaPorIntegrantes(
                usuarioService.obtenerUsuarioPorID(idUsuarioConsulta).getId(),
                usuarioService.obtenerUsuarioActual().getId()
        );

        if (sala == null)
            throw new ErrorException404(GeneralConstants.MENSAJE_GENERICO_NO_EXISTE);

        List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(sala.getId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                SalaMapper.salaResponse(sala, integrantes)
        ));
    }

}
