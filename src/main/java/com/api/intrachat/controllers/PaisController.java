package com.api.intrachat.controllers;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.PaisRequest;
import com.api.intrachat.services.interfaces.campania.IPaisService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.PaisMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/paises")
public class PaisController {

    private final IPaisService paisService;

    public PaisController(IPaisService paisService) {
        this.paisService = paisService;
    }

    // Entidad - http://localhost:9890/api/v1/paises/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerPaisesPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                PaisMapper.paisResponse(paisService.obtenerPaisPorID(id))
        ));
    }

    // Lista Normal - http://localhost:9890/api/v1/paises
    @GetMapping
    public ResponseEntity<GeneralResponse<?>> buscarPais() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                paisService.obtenerPaises().stream()
                        .map(PaisMapper::paisResponse).toList()
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/paises/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarPaisesPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        paisService.obtenerPaisesPaginado(page, size)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/paises
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarPais(@RequestBody PaisRequest paisRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        paisService.crearPais(paisRequest)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/paises/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarPais(@PathVariable Long id,
                                                             @RequestBody PaisRequest paisRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        paisService.modificarPais(id, paisRequest)
                )
        );
    }

}
