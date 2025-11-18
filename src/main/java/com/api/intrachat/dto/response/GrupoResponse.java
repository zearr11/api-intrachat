package com.api.intrachat.dto.response;

import java.time.LocalDateTime;

public class GrupoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String urlFoto;
    private SalaResponse sala;
    private LocalDateTime ultimaModificacion;
    private Boolean estado;

}
