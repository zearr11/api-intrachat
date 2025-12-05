package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.CampaniaRequest;
import com.api.intrachat.dto.request.CampaniaRequest2;
import com.api.intrachat.services.interfaces.campania.ICampaniaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/campanias")
public class CampaniaController {

    private final ICampaniaService campaniaService;

    public CampaniaController(ICampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    // Entidad - http://localhost:9890/api/v1/campanias
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerCampaniaPorID(@PathVariable Long id) {
        /*
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                CampaniaMapper.campaniaResponse(campaniaService.obtenerCampaniaPorID(id))
        ));
        */
        return null;
    }

    // Lista Normal - http://localhost:9890/api/v1/campanias/empresas/idempresa
    @GetMapping("/empresas/{idEmpresa}")
    public ResponseEntity<GeneralResponse<?>> buscarCampaniasPorEmpresa(@PathVariable Long idEmpresa) {
        /*
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                campaniaService.obtenerCampaniasPorEmpresa(idEmpresa).stream()
                        .map(CampaniaMapper::campaniaResponse).toList()
        ));
        */
        return null;
    }

    // Lista Paginada - http://localhost:9890/api/v1/campanias/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarCampaniasPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) Boolean estado, @RequestParam(required = false) String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                campaniaService.obtenerCampaniasPaginado(page, size, (estado == null || estado), filtro)
        ));
    }

    // Mensaje - http://localhost:9890/api/v1/campanias
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> crearCampania(@RequestBody CampaniaRequest campaniaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(campaniaService.crearCampania(campaniaRequest))
        );
    }

    // Mensaje - http://localhost:9890/api/v1/campanias/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarCampania(@PathVariable Long id,
                                                                 @RequestBody CampaniaRequest2 campaniaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(campaniaService.modificarCampania(id, campaniaRequest))
        );
    }

}
