package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.services.interfaces.chat.IMensajeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/mensajes")
public class MensajeController {

    private final IMensajeService mensajeService;

    public MensajeController(IMensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<GeneralResponse<?>> buscarMensajesPaginado(
            @PathVariable Long idSala,
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(defaultValue = "") String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        mensajeService.obtenerMensajesPorSala(page, size, filtro, idSala)
                )
        );
    }

}
