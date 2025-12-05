package com.api.intrachat.repositories.campania.projections;

import java.time.LocalDateTime;

public interface CampaniaProjection {

    Long getId();
    String getNombreComercialEmpresa();
    String getAreaAtencion();
    String getMedioComunicacion();

    Integer getTotalOperacionesActivas();
    Integer getTotalOperacionesInactivas();

    Integer getTotalEquiposActivos();
    Integer getTotalEquiposInactivos();

    Integer getTotalUsuariosActivos();
    Integer getTotalUsuariosInactivos();

    Boolean getEstado();
    LocalDateTime getFechaCreacion();
    LocalDateTime getUltimaModificacion();

}
