package com.api.intrachat.controllers;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.AreaRequest;
import com.api.intrachat.services.interfaces.campania.IAreaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.AreaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/areas")
public class AreaController {

    private final IAreaService areaService;

    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    // Entidad - http://localhost:9890/api/v1/areas/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerAreaPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                AreaMapper.areaResponse(areaService.obtenerAreaPorID(id))
        ));
    }

    // Lista Normal - http://localhost:9890/api/v1/areas
    @GetMapping
    public ResponseEntity<GeneralResponse<?>> buscarAreas() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                areaService.obtenerAreas().stream()
                        .map(AreaMapper::areaResponse).toList()
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/areas/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarAreasPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        areaService.obtenerAreasPaginado(page, size)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/areas
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarArea(@RequestBody AreaRequest areaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        areaService.crearArea(areaRequest)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/areas/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarArea(@PathVariable Long id,
                                                             @RequestBody AreaRequest areaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        areaService.modificarArea(id, areaRequest)
                )
        );
    }

}
