package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.services.interfaces.campania.IEquipoService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.EquipoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/equipos")
public class EquipoController {

    private final IEquipoService equipoService;

    public EquipoController(IEquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Entidad - http://localhost:9890/api/v1/equipos/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerEquipoPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(null
                // EquipoMapper.equipoResponse(equipoService.obtenerEquipoPorID(id))
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/equipos/usuarios/paginacion
    @GetMapping("/usuarios/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarUsuariosEquipoPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) Boolean estado, @RequestParam(required = false) Long idCampania,
            @RequestParam(required = false) Long idOperacion, @RequestParam(required = false) Long idEquipo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.obtenerUsuariosEquipoPaginado(
                                page, size, (estado == null || estado), idCampania,
                                idOperacion, idEquipo
                        )));
    }

    // Lista Paginada - http://localhost:9890/api/v1/equipos/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarEquiposPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false, defaultValue = "true") Boolean estado,
            @RequestParam(required = false) String filtro
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(equipoService.obtenerEquiposPaginado(
                                page, size, estado, filtro)));
    }

    // Mensaje - http://localhost:9890/api/v1/equipos
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarEquipo(@RequestBody EquipoRequest equipoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.crearEquipo(equipoRequest)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/equipos/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> deshabilitarEquipo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.deshabilitarEquipo(id)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/equipos/idEquipo/usuarios/idUsuario
    @PostMapping("/{idEquipo}/usuarios/{idUsuario}")
    public ResponseEntity<GeneralResponse<?>> agregarUsuarioAEquipo(@PathVariable Long idEquipo,
                                                                    @PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.agregarUsuarioAEquipo(idEquipo, idUsuario)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/equipos/idEquipo/usuarios/idUsuario
    @PutMapping("/{idEquipo}/usuarios/{idUsuario}")
    public ResponseEntity<GeneralResponse<?>> cambiarPermisoDeUsuarioEnEquipo(@PathVariable Long idEquipo,
                                                                              @PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.cambiarPermisoDeUsuarioEnEquipo(idEquipo, idUsuario)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/equipos/idEquipo/usuarios/idUsuario
    @PatchMapping("/{idEquipo}/usuarios/{idUsuario}")
    public ResponseEntity<GeneralResponse<?>> finalizarRelacionDeUsuarioConEquipo(@PathVariable Long idEquipo,
                                                                                  @PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        equipoService.finalizarRelacionDeUsuarioConEquipo(idEquipo, idUsuario)
                )
        );
    }

}
