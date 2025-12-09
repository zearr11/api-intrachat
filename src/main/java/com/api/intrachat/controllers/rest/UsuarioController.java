package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.request.UsuarioRequest;
import com.api.intrachat.dto.request.UsuarioRequest2;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.utils.enums.Cargo;
import com.api.intrachat.utils.mappers.UsuarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${app.prefix}/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Entidad - http://localhost:9890/api/v1/usuarios/actual
    @GetMapping("/actual")
    public ResponseEntity<GeneralResponse<?>> obtenerDatosUsuarioActual() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                UsuarioMapper.usuarioResponse(usuarioService.obtenerUsuarioActual())
        ));
    }

    // Entidad - http://localhost:9890/api/v1/usuarios/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerUsuarioPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                UsuarioMapper.usuarioResponse(usuarioService.obtenerUsuarioPorID(id))
        ));
    }

    @GetMapping("/paginacion/operaciones")
    public ResponseEntity<GeneralResponse<?>> buscarUsuariosSinOperacion(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(usuarioService.obtenerUsuariosDeOperacionRegular(
                        page, size, filtro
                ))
        );
    }

    @GetMapping("/paginacion/operaciones/asociados")
    public ResponseEntity<GeneralResponse<?>> buscarUsuariosSinOperacionIncluyendoConOperacionActual(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Long idOperacion) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(usuarioService.obtenerUsuariosDeOperacionEdit(
                        page, size, filtro, idOperacion
                ))
        );
    }

    @GetMapping("/paginacion/equipos")
    public ResponseEntity<GeneralResponse<?>> buscarUsuariosConEquipoYSinCampaniaPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Cargo cargo,
            @RequestParam(required = false) Long idEquipo) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(usuarioService.obtenerUsuariosDeEquipoYSinCampania(
                        page, size, (estado == null || estado), cargo, filtro, idEquipo)
                )
        );
    }

    // Lista Paginada - http://localhost:9890/api/v1/usuarios/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarUsuariosPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(required = false) Boolean estado,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Cargo cargo,
            @RequestParam(required = false) Boolean enCampania) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(usuarioService.obtenerUsuariosPaginado(
                        page, size, (estado == null || estado), filtro, cargo, enCampania)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/usuarios
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseConstruct.generarRespuestaExitosa(
                usuarioService.crearUsuario(usuarioRequest)
        ));
    }

    // Mensaje - http://localhost:9890/api/v1/usuarios/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarUsuario(@PathVariable Long id,
                                                                @RequestBody UsuarioRequest2 usuarioRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                usuarioService.modificarUsuario(id, usuarioRequest)
        ));
    }

    // Mensaje - http://localhost:9890/api/v1/usuarios/id
    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarFotoUsuario(@PathVariable Long id,
                                                                    @RequestPart(required = false) MultipartFile imagenUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                usuarioService.modificarImagenUsuario(id, imagenUsuario)
        ));
    }

}
