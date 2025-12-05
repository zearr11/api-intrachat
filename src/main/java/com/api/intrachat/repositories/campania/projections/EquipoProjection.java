package com.api.intrachat.repositories.campania.projections;

public interface EquipoProjection {

    Long getIdEquipo();
    String getSede();
    String getCampania();
    Boolean getEstado();
    String getJefeOperacion();
    String getSupervisor();
    String getNombreEquipo();
    Integer getIntegrantesActivos();
    Integer getIntegrantesInactivos();
    Double getPromedioMensajesDiarios();

}
