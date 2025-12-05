package com.api.intrachat.repositories.campania.projections;

import java.time.LocalDateTime;

public interface OperacionProjection {

    Long getId();
    String getCampania();
    String getSede();
    String getJefeOperacion();

    Integer getTotalEquiposOperativos();
    Integer getTotalEquiposInoperativos();

    Integer getTotalUsuariosOperativos();
    Integer getTotalUsuariosInoperativos();

    LocalDateTime getFechaCreacion();
    LocalDateTime getFechaFinalizacion();

}
