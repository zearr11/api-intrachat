package com.api.intrachat.repositories.general.projections;

public interface UsuariosPorMesProjection {
    Integer getMes();
    Long getCantidad();

    /*
    default String getNombreMes() {
        return java.time.Month.of(getMes())
                .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "ES"))
                .toUpperCase();
    }
    */
}
