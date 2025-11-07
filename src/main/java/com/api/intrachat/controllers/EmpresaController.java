package com.api.intrachat.controllers;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.EmpresaRequest;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.mappers.EmpresaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/empresas")
public class EmpresaController {

    private final IEmpresaService empresaService;

    public EmpresaController(IEmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Entidad - http://localhost:9890/api/v1/empresas/id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> obtenerEmpresaPorID(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                EmpresaMapper.empresaResponse(empresaService.obtenerEmpresaPorID(id))
        ));
    }

    // Lista Normal - http://localhost:9890/api/v1/empresas
    @GetMapping
    public ResponseEntity<GeneralResponse<?>> buscarEmpresas() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.generarRespuestaExitosa(
                empresaService.obtenerEmpresas().stream()
                        .map(EmpresaMapper::empresaResponse).toList()
        ));
    }

    // Lista Paginada - http://localhost:9890/api/v1/empresas/paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<GeneralResponse<?>> buscarEmpresasPaginado(
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        empresaService.obtenerEmpresasPaginado(page, size)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/empresas
    @PostMapping
    public ResponseEntity<GeneralResponse<?>> registrarEmpresa(@RequestBody EmpresaRequest empresaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.generarRespuestaExitosa(
                        empresaService.crearEmpresa(empresaRequest)
                )
        );
    }

    // Mensaje - http://localhost:9890/api/v1/empresas/id
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> actualizarEmpresa(@PathVariable Long id,
                                                                @RequestBody EmpresaRequest empresaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        empresaService.modificarEmpresa(id, empresaRequest)
                )
        );
    }

}
