package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/sedes")
public class SedeController {

    private final ISedeService sedeService;

    public SedeController(ISedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarSedesPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false, defaultValue = "true") Boolean estado,
            @RequestParam(required = false) String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        sedeService.obtenerSedesPaginado(page, size, estado, filtro)
                )
        );
    }

}
