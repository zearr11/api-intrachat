package com.api.intrachat.utils.helpers;

public class UsuarioHelper {

    public static String obtenerNombreCorto(String nombres, String apellidos) {
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("El parámetro 'nombres' no puede ser nulo o vacío.");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("El parámetro 'apellidos' no puede ser nulo o vacío.");
        }

        String primerNombre = nombres.trim().split("\\s+")[0];
        String primerApellido = apellidos.trim().split("\\s+")[0];

        return primerNombre + " " + primerApellido;
    }

}
