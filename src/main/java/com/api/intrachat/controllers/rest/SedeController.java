package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.SedeRequest;
import com.api.intrachat.dto.request.SedeRequest2;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.SedeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/sedes")
public class SedeController {

    private final ISedeService sedeService;

    public SedeController(ISedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerSedePorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                SedeMapper.sedeResponse(sedeService.obtenerSedePorID(id))
        ));
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

    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarSede(@RequestBody SedeRequest sedeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(sedeService.crearSede(sedeRequest))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarSede(@PathVariable Long id,
                                                             @RequestBody SedeRequest2 sedeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(sedeService.modificarSede(id, sedeRequest))
        );
    }

}
