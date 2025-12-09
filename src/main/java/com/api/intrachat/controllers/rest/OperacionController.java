package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.OperacionRequest;
import com.api.intrachat.services.interfaces.campania.IOperacionService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.OperacionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/operaciones")
public class OperacionController {

    private final IOperacionService operacionService;

    public OperacionController(IOperacionService operacionService) {
        this.operacionService = operacionService;
    }

    // Entidad - http://localhost:9890/api/v1/operaciones/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerOperacionPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                OperacionMapper.operacionResponse(operacionService.obtenerOperacionPorID(id))
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/operaciones/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarOperacionesPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(defaultValue = "true") Boolean estado,
            @RequestParam(required = false) Long idCampania,
            @RequestParam(required = false) String filtro
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                    operacionService.obtenerOperacionesPaginado(page, size, estado, idCampania, filtro)
                ));
    }

    // Mensaje - http://localhost:9890/api/v1/operaciones
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarOperacion(@RequestBody OperacionRequest operacionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        operacionService.crearOperacion(operacionRequest)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> modificarOperacion(@PathVariable Long id,
                                                                 @RequestBody OperacionRequest operacionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(operacionService.modificarOperacion(id, operacionRequest))
        );
    }

    // Mensaje - http://localhost:9890/api/v1/operaciones/id
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> finalizarOperacion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(operacionService.finalizarOperacion(id))
        );
    }

}
