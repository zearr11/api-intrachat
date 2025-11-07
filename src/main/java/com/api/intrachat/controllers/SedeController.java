package com.api.intrachat.controllers;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.SedeRequest;
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

    // Entidad - http://localhost:9890/api/v1/sedes/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerSedePorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                SedeMapper.sedeResponse(sedeService.obtenerSedePorID(id))
        ));
    }

    // Lista Normal - http://localhost:9890/api/v1/sedes
    @GetMapping
    public ResponseEntity<GeneralResponse<?>> buscarSedes() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                sedeService.obtenerSedes().stream()
                        .map(SedeMapper::sedeResponse).toList()
        ));
    }

    // Lista Normal - http://localhost:9890/api/v1/sedes/paises/idpais
    @GetMapping("/paises/{idPais}")
    public ResponseEntity<GeneralResponse<?>> buscarSedesPorPais(@PathVariable Long idPais) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                sedeService.obtenerSedesPorPais(idPais).stream()
                        .map(SedeMapper::sedeResponse).toList()
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/sedes/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarSedesPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        sedeService.obtenerSedesPaginado(page, size)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/sedes
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarSede(@RequestBody SedeRequest sedeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        sedeService.crearSede(sedeRequest)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/sedes/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarSede(@PathVariable Long id,
                                                             @RequestBody SedeRequest sedeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        sedeService.modificarSede(id, sedeRequest)
                )
        );
    }

}
