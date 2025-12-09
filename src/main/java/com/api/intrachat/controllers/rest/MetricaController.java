package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.services.interfaces.other.IMetricaService;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/metricas")
public class MetricaController {

    private final IMetricaService metricaService;

    public MetricaController(IMetricaService metricaService) {
        this.metricaService = metricaService;
    }

    @GetMapping("/alertas")
    public ResponseEntity<GeneralResponse<?>> alertaUsuariosSinAsignar() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(metricaService.alertaUsuariosSinAsignar()));
    }

    @GetMapping("/general")
    public ResponseEntity<GeneralResponse<?>> infoGeneralEntidades() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(metricaService.infoGeneralEntidades()));
    }

    @GetMapping("/diarias")
    public ResponseEntity<GeneralResponse<?>> infoDiariaAreasImportantes() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(metricaService.infoDiariaAreasImportantes()));
    }

    @GetMapping("/usuarios/alta-anual")
    public ResponseEntity<GeneralResponse<?>> obtenerMetricaAltaUsuariosAnual(@RequestParam(defaultValue = "2025") Integer anio) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(metricaService.obtenerUsuariosDadosDeAltaAnual(anio)));
    }

    @GetMapping("/mensajes/promedio")
    public ResponseEntity<GeneralResponse<?>> obtenerPromedioMensajesAnual(@RequestParam(defaultValue = "2025") Integer anio) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(metricaService.obtenerMensajesPromedio(anio)));
    }

}
