package com.api.intrachat.repositories.campania.projections;

import java.time.LocalDateTime;

public interface EquipoProjection {

    Long getIdEquipo();
    String getSede();
    String getCampania();
    LocalDateTime getFechaCreacion();
    LocalDateTime getFechaCierre();
    String getJefeOperacion();
    String getSupervisor();
    String getNombreEquipo();
    Integer getIntegrantesActivos();
    Integer getIntegrantesInactivos();
    Double getPromedioMensajesDiarios();

}
